import java.awt.*;
import java.util.Objects;
import javax.swing.*;

public class Main {
    //init var
    private static final int BOARD_SIZE = 3;
    private static final Cell[] board = new Cell[BOARD_SIZE][BOARD_SIZE];
    private static Player currentTurn;
    private static Player[] players;
    private static JLabel currentTurnLabel;

    //inits all important stuff
    public static void main(String[] args){
        //inits players
        boolean isPlaying = true;
        final Player player1 = new Player("Player 1", Color.RED, 0);
        final Player player2 = new Player("Player 2", Color.BLUE, 1);
        players = new Player[]{player1, player2};
        currentTurn = player1;
        currentTurn.id = 0;

        //ui management inits
        final int WINDOW_SIZE = 500;
        final int DISPLACEMENT = 15;

        JFrame frame = new JFrame("tic tac toe");
        frame.setSize(WINDOW_SIZE,WINDOW_SIZE+(int)((double)WINDOW_SIZE/3));
        frame.setVisible(true);

        //the boxes go here
        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        cellPanel.setBounds(DISPLACEMENT, DISPLACEMENT, WINDOW_SIZE-DISPLACEMENT*2, WINDOW_SIZE-DISPLACEMENT);
        frame.add(cellPanel);

        //the text (such as "player X's turn!") goes here
        JPanel consolePanel = new JPanel();
        consolePanel.setBounds(0, WINDOW_SIZE, WINDOW_SIZE, WINDOW_SIZE/6);
        consolePanel.setLayout(null);
        frame.add(consolePanel);

        //add label to display current player's turn here
        currentTurnLabel = new JLabel(currentTurn.name+"'s turn!");
        currentTurnLabel.setBounds((int) (WINDOW_SIZE/2.75), WINDOW_SIZE, WINDOW_SIZE, WINDOW_SIZE/6);
        consolePanel.add(currentTurnLabel);

        //add logic to change player names here

        //inits all cells into board
        int CELL_COUNT = (int) Math.pow(BOARD_SIZE, 2);
        for(int pos = 0; pos < CELL_COUNT; pos++){new Cell(cellPanel, pos);}
        cellPanel.repaint();
        cellPanel.revalidate();
    }

    //checks for horizontal win
    private static boolean horizontalWinCheck(Player player){
        //for(Cell[] i:board){if (Arrays.equals(i, new String[]{player.icon, player.icon, player.icon})){return true;}}
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
        for(int i = BOARD_SIZE-1; i>0;i--){
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
    }*/

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
        int pos;

        private Cell(JPanel panel, int pos){
            taken = false;

            field.setBackground(Color.WHITE);
            panel.add(field);
            this.pos = pos;

            board[pos] = this;

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

    private static void turnAdvancement(){
        int playerID = currentTurn.id+1;
        if(playerID>=2){playerID = 0;}

        currentTurn = players[playerID];

        currentTurnLabel.setText(currentTurn.name+"'s turn!");
    }
}
