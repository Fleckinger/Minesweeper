package minesweeper;

import java.util.Random;

public class Minefield {
    private final Cell[][] field = new Cell[9][9];
    private final Cell[][] fieldForChecking = new Cell[9][9];
    final static char MINE = 'X';
    final static char FREE_SPACE = '.';
    final static char MARK = '*';
    final static char REVEALED = '/';
    boolean isFirstMove = true;


    public Minefield() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Cell(FREE_SPACE);
            }
        }
    }

    public void placeMines(int numberOfMines) {
        Random random = new Random();

        while (numberOfMines > 0) {
            int randomRow = random.nextInt(9);
            int randomColumn = random.nextInt(9);
            if (field[randomRow][randomColumn].getState() == FREE_SPACE) {
                field[randomRow][randomColumn].setState(MINE);
                numberOfMines--;
            }
        }

        for (int i = 0; i < field.length; i++) {
            System.arraycopy(field[i], 0, fieldForChecking[i], 0, field[i].length);
        }
    }

    public void printField() {

         System.out.println(" |1 2 3 4 5 6 7 8 9 |\n" +
                           "-|- - - - - - - - - |");


        for (int i = 0; i < field.length; i++) {
            System.out.print((i + 1) + "|");

            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j].isReveal()) {
                    if (field[i][j].isMarked()) {
                        System.out.print(MARK + " ");
                    } else  {
                        System.out.print(field[i][j].getState() + " ");
                    }
                } else if (field[i][j].isMarked()) {
                    System.out.print(MARK + " ");
                    } else {
                    System.out.print(FREE_SPACE + " ");
                }
            }
            System.out.print("|\n");
        }
        System.out.println("-|- - - - - - - - - |");
    }

    public Cell[][] getField() {
        Cell[][] fieldToReturn = new Cell[field.length][field.length];
        for (int i = 0; i < field.length; i++) {
            System.arraycopy(field[i], 0, fieldToReturn[i], 0, field[i].length);
        }
        return fieldToReturn;
    }

    public Cell[][] getFieldForChecking() {
        Cell[][] fieldToReturn = new Cell[fieldForChecking.length][fieldForChecking.length];
        for (int i = 0; i < fieldForChecking.length; i++) {
            System.arraycopy(fieldForChecking[i], 0, fieldToReturn[i], 0, field[i].length);
        }
        return fieldToReturn;
    }

    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    public void placeNumbersOfMinesAround() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (isMine(i, j)) {
                    checkMinesAround(i, j);
                }
            }
        }

    }

    private void checkMinesAround(int row, int column) {
        //start from left top to down right
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                //check array borders
                if ((i >= 0 && j >= 0 && i < field.length && j < field.length)) {
                    if (field[i][j].getState() == FREE_SPACE) {
                        field[i][j].setState('1');
                        field[i][j].setNumber(true);
                    } else if (field[i][j].isNumber()) { //field[i][j].getState() != FREE_SPACE && field[i][j].getState() != MINE
                        field[i][j].setState((char) ((field[i][j].getState()) + 1));
                    }
                }
            }
        }
    }

    public void revealAllEmptyCellsAround(int row, int column) {
        if (row < field.length && row >= 0 && column < field.length && column >= 0) {
            if (field[row][column].getState() == FREE_SPACE) {
                field[row][column].setReveal(true);
                field[row][column].setMarked(false);
                field[row][column].setState(REVEALED);

                revealAllEmptyCellsAround(row + 1, column);
                revealAllEmptyCellsAround(row - 1, column);
                revealAllEmptyCellsAround(row, column + 1);
                revealAllEmptyCellsAround(row, column - 1);
                revealAllEmptyCellsAround(row, column - 1);
                revealAllEmptyCellsAround(row - 1 , column + 1);
                revealAllEmptyCellsAround(row - 1 , column - 1);
                revealAllEmptyCellsAround(row + 1 , column + 1);
                revealAllEmptyCellsAround(row + 1 , column - 1);
            } else if (field[row][column].isNumber()) {
                field[row][column].setReveal(true);
                field[row][column].setMarked(false);
            }
        }
    }

    public void setMark(int row, int column) {
        field[row][column].setMarked(true);
    }

    public void removeMark(int row, int column) {
        field[row][column].setMarked(false);
    }

    public boolean isMine(int row, int column) {
        return field[row][column].getState() == MINE;
    }

    public void relocateMine(int row, int column) {

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++ ) {
                if (field[i][j].getState() == FREE_SPACE) {
                    field[i][j].setState(MINE);
                    fieldForChecking[i][j].setState(MINE);
                    field[row][column].setState(FREE_SPACE);
                    fieldForChecking[row][column].setState(FREE_SPACE);
                    isFirstMove = false;
                    return;
                }
            }
        }

    }


}
