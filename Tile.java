package org.cis1200.minesweeper;

public class Tile {
    private int mine;
    private boolean revealed;

    private boolean flagged;

    private boolean lost;
    private int adjacentMines;

    public Tile() {
        this.mine = 0;
        this.revealed = false;
        this.flagged = false;
        this.lost = false;
        this.adjacentMines = 0;
    }

    public Tile(int m, boolean r, boolean f, int aM) {
        this.mine = m;
        this.revealed = r;
        this.flagged = f;
        this.adjacentMines = aM;
        this.lost = false;
    }

    public void setMine() {
        mine = 1;
    }

    // Solely for testing purposes
    public void setNotMine() {
        mine = 0;
    };

    public void setRevealed() {
        revealed = true;
    }

    public void invertFlagged() {
        flagged = !flagged;
    }

    public void setLost() {
        lost = true;
    }

    public void setAdjacentMines(int mines) {
        adjacentMines = mines;
    }

    public int isMine() {
        return mine;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isLost() {
        return lost;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public String getInformation() {
        return mine + " " + revealed + " " + flagged + " " + adjacentMines;
    }
}
