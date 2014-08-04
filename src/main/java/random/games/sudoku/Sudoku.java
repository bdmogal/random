package random.games.sudoku;

/**
 * Created by bhooshan on 8/3/14.
 */

import java.util.*;

public class Sudoku {
    static final int MAX_ROW_COLUMN_SIZE = 9;
    static final int MAX_BOX_SIZE = 3;

    static void sudoku_solve(int [][] grid) {
        //your logic here
        if (solve(grid, 0, 0)) {
            print(grid);
        }
        else {
            System.err.println("Unable to solve!");
        }
    }

    /**
     * Recursive solve
     */
    private static boolean solve(int [][] puzzle, int i, int j) {
        // if this row is done, move to next row
        if (i == 9) {
            i = 0;
            // if all columns are done, we're done
            if (++j == 9) {
                return true;
            }
        }

        if (!empty(puzzle, i, j)) {
            return solve(puzzle, i+1, j);
        }

        // try a value between 1 to 9 for this (i, j)
        for(int val = 1; val <= MAX_ROW_COLUMN_SIZE; val++) {
            if(valid(puzzle, i, j, val)) {
                puzzle[i][j] = val;
                if (solve(puzzle, i+1, j)) {
                    return true;
                }
            }
        }
        puzzle[i][j] = 0;
        return false;
    }

    private static void print(int [][] puzzle) {
        for (int i = 0; i < MAX_ROW_COLUMN_SIZE; i++) {
            for (int j = 0; j < MAX_ROW_COLUMN_SIZE; j++) {
                System.out.print(puzzle[i][j]);
                if (j != MAX_ROW_COLUMN_SIZE-1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static boolean empty(int [][] puzzle, int row, int col) {
        return puzzle[row][col] == 0;
    }

    /**
     * Check to see if a number can be inserted in a (row, col) cell legally
     */
    private static boolean valid(int [][] puzzle, int row, int col, int val) {
        // invalid (return false) if the row already contains val
        for (int j = 0; j < MAX_ROW_COLUMN_SIZE; j++) {
            if (puzzle[row][j] == val) return false;
        }

        // invalid (return false) if the column already contains val
        for (int i = 0; i < MAX_ROW_COLUMN_SIZE; i++) {
            if (puzzle[i][col] == val) return false;
        }

        // invalid (return false) if the 3X3 box containing this (row, col) cell already contains val
        // find the boundaries of the box containing this cell
        int boxRow, boxCol;
        if (row < 3) boxRow = 0;
        else if (row > 5) boxRow = 6;
        else boxRow = 3;

        if (col < 3) boxCol = 0;
        else if (col > 5) boxCol = 6;
        else boxCol = 3;

        for (int i = boxRow; i < boxRow + MAX_BOX_SIZE; i++) {
            for (int j = boxCol; j < boxCol + MAX_BOX_SIZE; j++) {
                if (puzzle[i][j] == val) return false;
            }
        }

        // valid if neither row, column or 3X3 box contains val
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n;
        int board[][] = new int[9][9];

        n = in.nextInt();

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < 9; j++) {
                for(int k = 0; k < 9; k++) {
                    board[j][k] = in.nextInt();
                }
            }
            sudoku_solve(board);
        }
    }
}

