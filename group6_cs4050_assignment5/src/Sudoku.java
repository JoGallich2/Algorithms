import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sudoku {
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private int[][] board = new int[SIZE][SIZE];


    // reads file from given filename, then adds numbers from the file to
    // the 2D array "board".
    public void loadBoard (String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (scanner.hasNextInt()) {
                    board[i][j] = scanner.nextInt();
                }
            }
        }
        scanner.close();
    }

    // initiates the solveSudoku method by starting in the upper left corner (0, 0).
    public void solve() {
        if (solveSudoku(0, 0)) {
            System.out.println("Solved");
            printBoard();
        } else {
            System.out.println("No solution");
        }
    }

    // solves the game board recursively
    public boolean solveSudoku(int row, int col) {
        if (row == SIZE) {
            return true;
        }
        int nextCol = (col + 1) % SIZE;
        int nextRow = (col == SIZE - 1) ? row + 1 : row;

        if (board[row][col] != 0) {
            return solveSudoku(nextRow, nextCol);
        }

        // checks every number 1-9 to see if it is allowed to be in that spot
        // if it is safe, it places the number and recursively tries to solve the rest of the board.
        for (int i = 1; i <= SIZE; i++) {
            if(isSafe(row, col, i)) {
                board[row][col] = i;
                if (solveSudoku(nextRow, nextCol)) {
                    return true;
                }
                // This is the backtracking part
                board[row][col] = 0;
            }
        }
        return false;
    }

    // checks to see if a given number placement breaks any rules of Sudoku
    private boolean isSafe(int row, int col, int val) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) return false;
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == val || board[i][col] == val) return false;
        }
        int startRow = row - row % SUBGRID_SIZE;
        int startCol = col - col % SUBGRID_SIZE;

        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for(int j = 0; j < SUBGRID_SIZE; j++) {
                if (board[startRow + i][startCol + j] == val) return false;
            }
        }
        return true;
    }

    // prints the sudoku board in terminal
    private void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}