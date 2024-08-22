package org.cis1200.minesweeper;

import java.io.*;
import java.util.Random;

public class Minesweeper {

    private Tile[][] board;
    private int numMines;
    private boolean gameOver;
    private int numFlags;
    private int numRows;
    private int numColumns;

    // Constructor sets up game state.
    public Minesweeper() {
        reset();
    }

    // Allows user to reveal a given tie. Returns false if the given coordinates do
    // not exist or if the tile is not in
    // a state to be revealed. Sets gameOver to true if the Tile is a bomb. Call
    // revealNeighbors to recursively reveal
    // safe adjacent tiles. Column row are swapped parameters due to input coming in
    // as (x,y) coordinates.
    public boolean playTurn(int c, int r) {
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length) {
            return false;
        }
        if (board[r][c].isRevealed() || gameOver || board[r][c].isFlagged()) {
            return false;
        } else {
            revealNeighbors(r, c);
            if (board[r][c].isMine() == 1) {
                board[r][c].setLost();
                gameOver = true;
            }
            return true;
        }
    }

    // Helper method for revealNeighbors that checks if a tile is safe to reveal
    private boolean isSafe(Tile a) {
        boolean isMine = a.isMine() == 1;
        return !isMine && !a.isRevealed();
    }

    // Recursive function that reveals adjacent safe neighbors when a safe tile is
    // clicked
    public void revealNeighbors(int r, int c) {
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length) {
            return;
        }
        Tile currentTile = board[r][c];
        if (!isSafe(currentTile)) {
            return;
        }
        if (currentTile.isFlagged()) {
            currentTile.invertFlagged();
            numFlags++;
        }
        currentTile.setRevealed();
        if (currentTile.getAdjacentMines() > 0) {
            return;
        }
        // Recursive function call here
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                revealNeighbors(r + i, c + j);
            }
        }
    }

    // Functionality for placing a flag on the board. Column row are swapped
    // parameters due to input coming
    // in as (x,y) coordinates.
    public int placeFlag(int c, int r) {
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length) {
            return -1;
        }
        if (gameOver || board[r][c].isRevealed()) {
            return -1;
        } else if (board[r][c].isFlagged()) {
            board[r][c].invertFlagged();
            numFlags++;
            return 0;
        } else {
            board[r][c].invertFlagged();
            numFlags--;
            return 1;
        }
    }

    public int getNumFlags() {
        return numFlags;
    }

    // Checks whether the player has won the game yet
    public boolean checkWinner() {
        if (numFlags >= 0) {
            for (Tile[] tiles : board) {
                for (int j = 0; j < board[0].length; j++) {
                    if (!tiles[j].isRevealed() && !tiles[j].isFlagged()) {
                        return false;
                    }
                }
            }
            gameOver = true;
            return true;
        } else {
            return false;
        }
    }

    // printGameState prints the current game state for debugging.
    public void printGameState() {
        for (Tile[] tiles : board) {
            System.out.println();
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(tiles[j].isMine() + " ");
            }
        }
        System.out.println();
    }

    // Places the set number of mines at random locations across the board
    public void mineSetter() {
        int numRows = board.length;
        int numColumns = board[0].length;
        int placed = 0;
        while (placed < numMines) {
            Random rand = new Random();
            int mineRow = rand.nextInt(numRows);
            int mineColumn = rand.nextInt(numColumns);
            if (board[mineRow][mineColumn].isMine() == 0) {
                board[mineRow][mineColumn].setMine();
                placed++;
            }
        }
    }

    // Updates each Tile objects adjacent mine count using the countAdjacentMines
    // helper method. Made public for testing
    // purposes.
    public void calculateAdjacentMinesCounts() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].setAdjacentMines(countAdjacentMines(i, j));
            }
        }
    }

    // Helper function for calculateAdjacentMinesCounts that calculate how many
    // adjacent mines there are.
    private int countAdjacentMines(int r, int c) {
        int count = 0;
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (i >= 0 && i < board.length && j >= 0 && j < board[0].length
                        && !(i == r && j == c)) {
                    if (board[i][j].isMine() == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // reset (re-)sets the game state to start a new game.
    public void reset() {
        board = new Tile[8][10];
        numRows = 8;
        numColumns = 10;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Tile();
            }
        }
        numMines = 10;
        numFlags = numMines;
        gameOver = false;
        mineSetter();
        calculateAdjacentMinesCounts();
        printGameState();
    }

    // Save returns 0 if save isn't allowed, -1 for error, and 1 for a successful
    // save.
    public int save() {
        if (!gameOver) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("files/status.txt"))) {
                // Write Minesweeper status to the file
                writer.write(String.valueOf(numFlags));
                writer.newLine();
                writer.write(String.valueOf(numMines));
                writer.newLine();
                writer.write(String.valueOf(gameOver));
                writer.newLine();
                writer.write(String.valueOf(numRows));
                writer.newLine();
                writer.write(String.valueOf(numColumns));
                writer.newLine();
                for (Tile[] tiles : board) {
                    for (int j = 0; j < board[0].length; j++) {
                        Tile curTile = tiles[j];
                        writer.write(curTile.getInformation());
                        writer.newLine();
                    }
                }
                writer.flush();
                writer.close();
                return 1;
            } catch (IOException e) {
                return -1;
            }
        }
        return 0;
    }

    // Save returns false if it cannot save, and true for a successful save.
    public boolean load() {
        try (BufferedReader br = new BufferedReader(new FileReader("files/status.txt"))) {
            numFlags = Integer.parseInt(br.readLine());
            numMines = Integer.parseInt(br.readLine());
            gameOver = Boolean.parseBoolean(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            numColumns = Integer.parseInt(br.readLine());
            board = new Tile[numRows][numColumns];
            String nextLine = br.readLine();
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColumns; j++) {
                    String[] tileData = nextLine.split(" ");
                    int mine = Integer.parseInt(tileData[0]);
                    boolean revealed = Boolean.parseBoolean(tileData[1]);
                    boolean flagged = Boolean.parseBoolean(tileData[2]);
                    int adjacentMines = Integer.parseInt(tileData[3]);
                    board[i][j] = new Tile(mine, revealed, flagged, adjacentMines);
                    nextLine = br.readLine();
                }
            }
            br.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // The rest of the methods are "get" methods for the GameBoard to access or for
    // GameTest testing purposes.
    // Column row are swapped parameters due to input coming in as (x,y)
    // coordinates.
    public boolean getCellRevealed(int c, int r) {
        return board[r][c].isRevealed();
    }

    public boolean getCellFlagged(int c, int r) {
        return board[r][c].isFlagged();
    }

    public boolean getCellLost(int c, int r) {
        return board[r][c].isLost();
    }

    public int getCellAdjacentMines(int c, int r) {
        return board[r][c].getAdjacentMines();
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    // Solely for testing purposes
    public void setGameOver() {
        gameOver = true;
    }

    // Solely for testing purposes
    // Column row are swapped parameters due to consistency purposes with Get
    // methods.
    public void setNotMine(int c, int r) {
        if (board[r][c].isMine() == 1) {
            board[r][c].setNotMine();
            numMines--;
            calculateAdjacentMinesCounts();
        }
    }

    // Solely for testing purposes
    // Column row are swapped parameters due to consistency purposes with Get
    // methods.
    public void setMine(int c, int r) {
        if (board[r][c].isMine() != 1) {
            board[r][c].setMine();
            numMines++;
            calculateAdjacentMinesCounts();
        }
    }

    // Solely for testing purposes
    public void setNumFlags(int f) {
        numFlags = f;
    }

    public static void main(String[] args) {
        Minesweeper t = new Minesweeper();
    }
}
