import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    private static final int rows = 9;
    private static final int columns = 9;
    private static int[][] bombsGrid = new int[rows][columns];  //grid with bombs for checking
    private static int[][] playerGrid = new int[rows][columns]; //player board

    private static void check (int row, int column) {
        int sum = 0;
        for (int i = row - 1; i <= row + 1; i++)
            for (int j = column - 1; j <= column + 1; j++)
                try {
                    if (bombsGrid[i][j] == 1) sum++;
                } catch (Exception ignored) {}

        if (sum != 0)
            playerGrid[row][column] = sum;
        else {
            playerGrid[row][column] = -2;
                for (int i = row - 1; i <= row + 1; i++)
                    for (int j = column - 1; j <= column + 1; j++)
                        try {
                            if (playerGrid[i][j] == 0)
                                check(i, j);
                        } catch (Exception ignored) {}
        }
    }

    private static void revealBombs() { // reveal all the bombs on the user grid
        for (int i = 0; i < bombsGrid.length; i++)
            for (int j = 0; j < bombsGrid[i].length; j++)
                if (bombsGrid[i][j] == 1)
                    playerGrid[i][j] = -3; // -3 means not selected bomb
    }
    
    private static void printPlayerGrid() {
        for (int i = 0; i < playerGrid.length; i++) {
            for (int j = 0; j < playerGrid[i].length; j++) {
                if (playerGrid[i][j] == 0)
                    System.out.print("\u25A1 ");
                else if (playerGrid[i][j] == -1)
                    System.out.print("b ");
                else if (playerGrid[i][j] == -2)
                    System.out.print("_ ");
                else if (playerGrid[i][j] == -3)
                    System.out.print("B ");
                else
                    System.out.print(playerGrid[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isWon(int numberOfBombs) { //чекаю не выиграл ли я еще, если кол-во нераскрытых клеток равно кол-ву бомб, то я выиграл
        int uncoveredCells = 0;
        for (int i = 0; i < playerGrid.length; i++)
            for (int j = 0; j < playerGrid[i].length; j++)
                if (playerGrid[i][j] == 0)
                    uncoveredCells++;
        return uncoveredCells == numberOfBombs;
    }

    private static int plantedMines() {
        Random random = new Random();

        //moderate game: 10%-20% of the board are bombs
        int min = (int)(rows*columns*0.1);
        int max = (int)(rows*columns*0.2);
        int numberOfBombs = random.nextInt(max-min) + min;

        //planting bombs
        for (int i = 0; i < numberOfBombs; i++) {
            int k = random.nextInt(9);
            int m = random.nextInt(9);
            if (bombsGrid[k][m] == 1) {
                i--;
                continue;
            }
            bombsGrid[k][m] = 1;
        }
        return numberOfBombs;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean continueGame = true;
        System.out.println("Welcome to MineSweeper Game!!!");

        while (continueGame) {
            System.out.println("Good Luck!!!");
            int numberOfBombs = plantedMines();

            //player board
            for (int i = 0; i < rows; i++)
                for(int j = 0; j < columns; j++)
                    playerGrid[i][j] = 0;
            printPlayerGrid();

            //game
            while (true) {
                System.out.print("Enter your move (row[1-9] column[1-9]): "); //add "next move"
                try {
                    String playerInput = input.nextLine();
                    String numbers[] = playerInput.split(" ");
                    if (numbers.length > 2)
                        throw new InputMismatchException();

                    int row = Integer.parseInt(numbers[0]);
                    row--;
                    int column = Integer.parseInt(numbers[1]);
                    column--;

                    if (playerGrid[row][column] != 0) {
                        System.out.println("The cell is open. Select another one. ");
                        continue;
                    }
                    if (bombsGrid[row][column] == 1) { //the cell is a bomb
                        revealBombs();
                        playerGrid[row][column] = -1; // -1 means selected bomb
                        printPlayerGrid();
                        System.out.print("Oops! You lose! ");
                        break;
                    }
                    else {
                        check(row, column);
                        if (isWon(numberOfBombs)) {
                            revealBombs();
                            printPlayerGrid();
                            System.out.println("You win. Congrats! ");
                            break;
                        }
                        printPlayerGrid();
                    }
                } catch (InputMismatchException wrongInput) {
                    System.out.println("Invalid move. Try again. ");
                } catch (ArrayIndexOutOfBoundsException outOfBand) {
                    System.out.println("Your move is out of the board. Try again. ");
                } catch (NumberFormatException notInteger) {
                    System.out.println("You entered not an integer. Try again. ");
                }
            }

            //next game?
            while (true) {
                System.out.print("Would you like to play again? (Y/N): ");
                String choice = input.nextLine();
                if (choice.equals("N")) {
                    continueGame=false;
                    break;
                }
                else if (choice.equals("Y"))
                    break;
                else
                    System.out.println("Wrong input. ");
            }
        }
        input.close();
    }
}
