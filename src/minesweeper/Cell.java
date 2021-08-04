package minesweeper;


public class Cell {
    private boolean isReveal;
    private boolean isNumber;
    private boolean isMarked;
    private char state;

    public Cell(char state) {
        this.state = state;
        isReveal = false;
    }

    public boolean isReveal() {
        return isReveal;
    }

    public void setReveal(boolean reveal) {
        isReveal = reveal;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public void setNumber(boolean number) {
        isNumber = number;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return Character.toString(state);
    }
}

