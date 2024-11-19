import java.awt.*;
import java.util.Objects;
import javax.swing.*;

public class Main {
    //init var
    private static final int BOARD_SIZE = 3;
    private static final Cell[][] board = new Cell[BOARD_SIZE][BOARD_SIZE];
    private static final String EMPTY_BOX = "-";
    private static boolean isWon = false;
    private static Player currentTurn;

    //inits all important stuff
    public static void main(String[] args){
        //inits players
        boolean isPlaying = true;
        final Player player1 = new Player("Player 1", Color.RED);
        final Player player2 = new Player("Player 2", Color.BLUE);
        Player[] players = new Player[]{player1, player2};
        currentTurn = player1;

        //ui management inits
        final int WINDOW_SIZE = 500;
        final int DISPLACEMENT = 15;

        JFrame frame = new JFrame("tic tac toe");
        frame.setSize(WINDOW_SIZE,WINDOW_SIZE+WINDOW_SIZE/6);
        frame.setVisible(true);

        //the boxes go here
        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        cellPanel.setBounds(DISPLACEMENT, DISPLACEMENT, WINDOW_SIZE-DISPLACEMENT*2, WINDOW_SIZE-DISPLACEMENT);
        frame.add(cellPanel);

        //the text (such as "player X's turn!") goes here
        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(null);
        consolePanel.setBounds(WINDOW_SIZE, WINDOW_SIZE, WINDOW_SIZE, WINDOW_SIZE/6);
        frame.add(consolePanel);

        //add logic to change player names here

        //inits all cells into board
        for(int row = 0;row<BOARD_SIZE;row++){
            for(int col = 0; col <BOARD_SIZE; col++){
                new Cell(cellPanel, row,col);
            }
        }

        //main gameplay loop
        do{


            //if win, do a YN input for the isplaying bool
            if(isWon){
                //isPlaying = InputHelper.getYN("Would you like to play again?");
            }

        }while(isPlaying);
    }

    //this is a boolean bc u need a way to tell the main method the game is over so they can prompt to play again
    private static void setCell(Player player){
        //tbh i could just use a regex to ensure the player inputs something along the lines of [X, Y] and then read the R/C using substrings and integer.parseInt but tbh ive already overengineered this program enough
        boolean legal;
        int row ;
        int col;

        //prevents game from still playing if the player already won
        if(!isWon){
            //do {
                //row = InputHelper.getRangedInt(player.name + ", enter move row",1,3);
                //col = InputHelper.getRangedInt(player.name + ", enter move column",1,3);
                //legal = legalMoveCheck(row, col);
                //if (!legal) {System.out.println("Sorry, you entered invalid options. Try again.");}
            //}while(!legal);

            //board[row-1][col-1] = player.icon; //must be appended by 1 because the indexes are 0-2
            isWon = winCheck(player);
        }
    }

    private static boolean legalMoveCheck(int row, int col){
        boolean rowCheck = false;
        boolean colCheck = false;
        boolean alreadyExists = true;
        if(row>=1&&row<=BOARD_SIZE){rowCheck=true;}
        if(col>=1&&col<=BOARD_SIZE){colCheck=true;}
        if(!Objects.equals(board[row - 1][col - 1], EMPTY_BOX)){alreadyExists = false;}
        return rowCheck&&colCheck&&alreadyExists;
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
    }

    private static class Player{
        String name;
        Color color;

        private Player(String name, Color color){
            this.name = name;
            this.color = color;
        }
    }
    private static class Cell{
        boolean taken;
        JButton field = new JButton();

        private Cell(JPanel panel, int row, int col){
            taken = false;

            field.setBackground(Color.WHITE);
            panel.add(field);

            board[row][col] = this;

            field.addActionListener(e -> {
                setBackground(currentTurn);
                turnAdvancement();
            });
        }

        private void setBackground(Player player){
            taken = true;
            field.setBackground(player.color);
        }
    }
}
