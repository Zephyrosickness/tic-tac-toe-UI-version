import java.awt.*;
import javax.swing.*;

public class Main{
    //init var
    private static final int BOARD_SIZE = 3;
    private static final Cell[][] board = new Cell[BOARD_SIZE][BOARD_SIZE];
    private static Player currentTurn;
    private static Player[] players;
    private static JLabel currentTurnLabel;

    //inits all important stuff
    public static void main(String[] args){
        //inits players
        final Player player1 = new Player("Player 1", Color.RED, 0);
        final Player player2 = new Player("Player 2", Color.BLUE, 1);
        players = new Player[]{player1, player2};

        //ui management inits
        final int WINDOW_SIZE = 500;
        final int DISPLACEMENT = 15;

        JFrame gameFrame = new JFrame("tic tac toe");
        gameFrame.setSize(WINDOW_SIZE,WINDOW_SIZE+(int)((double)WINDOW_SIZE/3));

        //the boxes go here
        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        cellPanel.setBounds(DISPLACEMENT, DISPLACEMENT, WINDOW_SIZE-DISPLACEMENT*2, WINDOW_SIZE-DISPLACEMENT);
        gameFrame.add(cellPanel);

        //the text (such as "player X's turn!") goes here
        JPanel consolePanel = new JPanel();
        consolePanel.setBounds(0, WINDOW_SIZE, WINDOW_SIZE, WINDOW_SIZE/6);
        consolePanel.setLayout(null);
        gameFrame.add(consolePanel);

        //add label to display current player's turn here
        currentTurnLabel = new JLabel("PLACEHOLDER");
        currentTurnLabel.setBounds((int) (WINDOW_SIZE/2.75), WINDOW_SIZE, WINDOW_SIZE, WINDOW_SIZE/6);
        consolePanel.add(currentTurnLabel);


        JFrame titleFrame = new JFrame("tic tac toe");
        titleFrame.setResizable(false);
        titleFrame.setSize((int) (((double) WINDOW_SIZE /2)*1.5), (int) (((double) WINDOW_SIZE /3)*1.5));
        titleFrame.setVisible(true);

        JPanel titlePanel = new JPanel();
        titlePanel.setSize(titleFrame.getWidth(), titleFrame.getHeight());
        titlePanel.setLayout(null);
        titleFrame.add(titlePanel);

        JLabel nameTitle = new JLabel("Tic-Tac-Toe (Swing Version)");
        nameTitle.setBounds((titlePanel.getWidth()/2)-80, 0, 200,50);
        titlePanel.add(nameTitle);

        JButton playAgain = new JButton("Play!");
        playAgain.setBounds((titlePanel.getWidth()/2)-50, 50, 100, 50);
        titlePanel.add(playAgain);

        playAgain.addActionListener(e -> {
            cellPanel.removeAll();
            for(int row = 0; row < BOARD_SIZE; row++){for(int col = 0; col<BOARD_SIZE; col++){new Cell(cellPanel, row, col);}}
            //inits all cells into board
            currentTurn = players[0];
            currentTurnLabel.setText(currentTurn.name+"'s turn!");
            cellPanel.repaint();
            cellPanel.revalidate();

            gameFrame.setVisible(true);

        });


    }

    private static void turnAdvancement(){
        int playerID = currentTurn.id+1;
        if(playerID>=2){playerID = 0;}


        if(winCheck(currentTurn)){currentTurnLabel.setText(currentTurn.name+" won!");
        }else{
            currentTurn = players[playerID];
            currentTurnLabel.setText(currentTurn.name+"'s turn!");
        }
    }

    //checks for horizontal win
    private static boolean horizontalWinCheck(Player player){
        for(int row = 0; row<BOARD_SIZE; row++){
            boolean win = true;
            for(Cell[] col : board){
                if(!col[row].field.getBackground().equals(player.color)){
                    win = false;
                    break;
                }
            }
            if(win){return true;}
        }
        return false;
    }

    //checks for vertical win
    private static boolean verticalWinCheck(Player player){
        for(int col = 0; col<BOARD_SIZE; col++){
            boolean win = true;
            for(Cell[] row : board){
                if(!row[col].field.getBackground().equals(player.color)){
                    win = false;
                    break;
                }
            }
            if(win){return true;}
        }
        return false;
    }

    private static boolean diagonalWinCheck(Player player){
        boolean leftToRight = true;
        boolean rightToLeft = true;
        //checks if diagonal from left to right
        for(int i = 0; i<BOARD_SIZE;i++){
            if(!board[i][i].field.getBackground().equals(player.color)){
                leftToRight = false;
                break;
            }
        }
        //checks if diagonal from right to left
        for(int i = BOARD_SIZE-1; i>=0;i--){
            if(!board[i][(BOARD_SIZE-1)-i].field.getBackground().equals(player.color)){
                rightToLeft = false;
                break;
            }
        }
        return rightToLeft||leftToRight;
    }

    private static boolean tieCheck(){
        boolean isTied = true;

        for(Cell[] row:board){
            for(Cell col:row){
                if(col.taken){
                    isTied = false;
                    break;
                }
            }
        }
        return isTied;
    }

    private static boolean winCheck(Player player){
        boolean horizontal = horizontalWinCheck(player);
        boolean vertical = verticalWinCheck(player);
        boolean diagonal = diagonalWinCheck(player);
        boolean playerWon = horizontal||vertical||diagonal;
        boolean tie = tieCheck();

        if(horizontal||vertical||diagonal){System.out.println(player.name+" won!");
        }else if(tie){System.out.println("Tie!");}

        return tie||playerWon;
    }

    private static class Player{
        String name;
        Color color;
        int id;

        private Player(String name, Color color, int id){
            this.name = name;
            this.color = color;
            this.id = id;
        }
    }
    private static class Cell{
        boolean taken;
        JButton field = new JButton();
        int row;
        int col;

        private Cell(JPanel panel, int row, int col){
            taken = false;

            field.setBackground(Color.WHITE);
            panel.add(field);
            this.row = row;
            this.col = col;

            board[row][col] = this;

            field.addActionListener(e -> {setBackground();});
        }

        private void setBackground(){
            if(!taken) {
                taken = true;
                field.setBackground(currentTurn.color);
                turnAdvancement();
            }
        }
    }
}
