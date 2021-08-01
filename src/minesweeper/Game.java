package minesweeper;

import java.util.Scanner;

public class Game {
    Minefield minefield = new Minefield();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        int numberOfMines = scanner.nextInt();
        minefield.placeMines(numberOfMines);
        minefield.showNumberOfMines();
        minefield.printField();

        while (true) {
            System.out.println("Set/delete mine marks (x and y coordinates):");

            int y = scanner.nextInt() - 1;
            int x = scanner.nextInt() - 1;

            if ((x < 0 && y < 0) || (x > minefield.getField().length - 1 && y > minefield.getField().length - 1)) {
                System.out.println("Enter coordinates from 1 to 9.");
            } else if (minefield.isNumberHere(x, y)) {
                System.out.println("There is a number here!");
                continue;
            } else {
                playerGuess(x, y);
                minefield.printField();
            }

            if (isWin()) {
                System.out.println("Congratulations! You found all the mines!");
                break;
            }
        }
    }

    private void playerGuess(int x, int y) {
        if (minefield.getCell(x, y) == Minefield.FREE_SPACE || minefield.getCell(x, y) == Minefield.MINE) {
            minefield.setMark(x, y);
        } else if (minefield.getCell(x, y) == Minefield.MARK) {
            minefield.removeMark(x, y);
        }
    }

    private boolean isWin() {
        for (int i = 0; i < minefield.getField().length; i++) {
            for (int j = 0; j < minefield.getField().length; j++) {
                if (minefield.getFieldForChecking()[i][j] == Minefield.MINE
                        && minefield.getField()[i][j] != Minefield.MARK) {
                    return false;
                }
            }
        }
        return true;
    }
}
