package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Minefield {
    private final char[][] field = new char[9][9];
    private final char[][] fieldForChecking = new char[9][9];
    final static char MINE = 'X';
    final static char FREE_SPACE = '.';
    final static char MARK = '*';

    public Minefield() {
        for (char[] row: field) {
            Arrays.fill(row, '.');
        }
    }

    public void printField() {
        System.out.println(" |123456789|\n" +
                           "-|---------|");
        /*System.out.println(" |1 2 3 4 5 6 7 8 9 |\n" +
                           "-|- - - - - - - - - |");*/

        for (int i = 0; i < field.length; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == MINE) {
                    System.out.print(FREE_SPACE);
                } else {
                    System.out.print(field[i][j]);
                }
            }
            System.out.print("|\n");
        }
        /*System.out.println("-|- - - - - - - - - |");*/
        System.out.println("-|---------|");
    }

    public char[][] getField() {
        char[][] fieldToReturn = new char[field.length][field.length];
        for (int i = 0; i < field.length; i++) {
            System.arraycopy(field[i], 0, fieldToReturn[i], 0, field[i].length);
        }
        return fieldToReturn;
    }

    public char[][] getFieldForChecking() {
        char[][] fieldToReturn = new char[fieldForChecking.length][fieldForChecking.length];
        for (int i = 0; i < fieldForChecking.length; i++) {
            System.arraycopy(fieldForChecking[i], 0, fieldToReturn[i], 0, field[i].length);
        }
        return fieldToReturn;
    }

    public char getCell(int x, int y) {
        return field[x][y];
    }

    public void placeMines(int numberOfMines) {
        Random random = new Random();

        while (numberOfMines > 0) {
            int randomRow = random.nextInt(9);
            int randomColumn = random.nextInt(9);
            if(field[randomRow][randomColumn] == FREE_SPACE) {
                field[randomRow][randomColumn] = MINE;
                numberOfMines--;
            }
        }

        for (int i = 0; i < field.length; i++) {
            System.arraycopy(field[i], 0, fieldForChecking[i], 0, field[i].length);
        }
    }

    public void showNumberOfMines() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == MINE) {
                    checkMinesAround(i, j);
                }
            }
        }

    }

    private void checkMinesAround(int row, int column) {
        //start from left top to down right
        for (int i = row - 1; i < row + 2; i++) {
            for (int j = column - 1; j < column + 2; j++) {
                //check array borders
                if ((i >= 0 && j >= 0) && (i < field.length && j < field.length)) {
                    if (field[i][j] == FREE_SPACE) {
                        field[i][j] = '1';
                    } else if (field[i][j] != FREE_SPACE && field[i][j] != MINE) {
                        field[i][j]++;
                    }
                }
            }
        }
    }

    public boolean isNumberHere(int x, int y) {
        return field[x][y] != FREE_SPACE && field[x][y] != MINE && field[x][y] != MARK;
    }

    public void setMark(int x, int y) {
        field[x][y] = MARK;
    }

    public void removeMark(int x, int y) {
        field[x][y] = FREE_SPACE;
    }




}
