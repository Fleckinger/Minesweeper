package minesweeper;

import java.util.Scanner;

public class Game {
    Minefield minefield = new Minefield();


    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        int numberOfMines = scanner.nextInt();
        minefield.placeMines(numberOfMines);
        minefield.printField();

        while (true) {
            System.out.println("Set/unset mine marks or claim a cell as free:");

            int column = scanner.nextInt() - 1;
            int row = scanner.nextInt() - 1;
            String command = scanner.nextLine().trim();

            if (isValidInput(row, column, command)) {
                if (minefield.isFirstMove) {
                    playerGuess(row, column, command);
                    minefield.printField();
                } else if (minefield.isMine(row, column) && command.equalsIgnoreCase("free")) {
                    lossMessage();
                    break;
                } else {
                    playerGuess(row, column, command);
                    minefield.printField();
                }
                if (isWin()) {
                    System.out.println("Congratulations! You found all the mines!");

                    break;
                }
            }
        }
    }

    private void playerGuess(int row, int column, String playerGuess) {

        if ("free".equalsIgnoreCase(playerGuess)) {
            if (minefield.isFirstMove) {
                minefield.isFirstMove = false;
                if (minefield.isMine(row, column)) {
                    minefield.relocateMine(row, column);
                    minefield.placeNumbersOfMinesAround();
                    minefield.revealAllEmptyCellsAround(row, column);
                } else {
                    minefield.placeNumbersOfMinesAround();
                    minefield.revealAllEmptyCellsAround(row, column);
                }
            } else {
                minefield.revealAllEmptyCellsAround(row, column);
            }

        } else if (playerGuess.equalsIgnoreCase("mine")) {

            //TODO Решить расположение точек и слешей
            if (minefield.getCell(row, column).isMarked()) {
                minefield.removeMark(row, column);
            } else {
                minefield.setMark(row, column);
            }
        }
    }

    private boolean isValidInput(int row, int column, String command) {
        if ((column < 0 && row < 0) || (column > minefield.getField().length - 1 && row > minefield.getField().length - 1)) {
            System.out.println("Enter coordinates from 1 to 9.");
            return false;
        } /*else if (minefield.getField()[column][row].isNumber()) {
            System.out.println("There is a number here!");
            return false;
        }*/
        if (command != null && !command.isEmpty()) {
            if (command.equalsIgnoreCase("mine") && command.equalsIgnoreCase("free")) {
                System.out.println("Wrong command, please use \"mine\" or \"free\".");
                return false;
            }
        } else {
            System.out.println("Wrong command, please use \"mine\" or \"free\".");
            return false;
        }
        return true;
    }

    private void lossMessage() {
        minefield.printField();
        System.out.println("You stepped on a mine and failed!");
        System.out.println("|123456789|\n" +
                "-|---------|");
        for (int i = 0; i < minefield.getField().length; i++) {
            System.out.print((i + 1) + "|");

            for (int j = 0; j < minefield.getField()[i].length; j++) {
                if (minefield.getField()[i][j].isReveal()) {
                    System.out.print(minefield.getField()[i][j].getState());
                } else if (minefield.isMine(i, j)) {
                    System.out.print(Minefield.MINE);
                } else {
                    System.out.print(Minefield.FREE_SPACE);
                }
            }
            System.out.print("|\n");
        }
        System.out.println("-|---------|");
    }

    private boolean isWin() {
        return isWinByMarkAllMine() || isWinByOpenAllCell();
    }

    private boolean isWinByMarkAllMine() {
        for (int i = 0; i < minefield.getField().length; i++) {
            for (int j = 0; j < minefield.getField().length; j++) {
                if (minefield.getFieldForChecking()[i][j].getState() == Minefield.MINE
                        && !minefield.getField()[i][j].isMarked()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWinByOpenAllCell() {
        for (int i = 0; i < minefield.getField().length; i++) {
            for (int j = 0; j < minefield.getField().length; j++) {
                if (!minefield.getCell(i, j).isReveal() && !minefield.isMine(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
}

