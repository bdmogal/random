package random.algos.maze;

import java.io.IOException;

/**
 * Created by bmogal on 9/14/14.
 */
public class MazeSolver {

    public static void main(String [] args) {
        Maze maze = null;
        try {
            maze = new Maze("/maze2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        solve(maze, 1, 1);
    }


    public static void solve(Maze maze, int startRow, int startColumn) {
        if (navigate(maze, startRow, startColumn)) {
            System.out.println(maze);
        }
        else {
            System.out.println("unsolvable maze from position (" + startRow + ", " + startColumn + ")");
        }
    }

    public static boolean navigate(Maze maze, int row, int col) {
        // termination conditions first. backtrack at each
        // condition 1. outside range
        if (row < 0 || col < 0 || row >= maze.height() || col >= maze.width()) {
            return false;
        }
        // condition 2. wall
        if (maze.isWall(row, col)) {
            return false;
        }
        // condition 3. already visited this position
        if (maze.explored(row, col)) {
            return false;
        }

        // if you are at an edge (maxrow or maxcol), then you have solved the maze
        if (row == 0 || col == 0 || row == maze.height() - 1 || col == maze.width() - 1) {
            maze.mark(row, col);
            System.out.println("Escaped maze at (" + row + ", " + col + ")\n");
            return true;
        }

        maze.explore(row, col);
        if (navigate(maze, row, col - 1) ||         // try left
                navigate(maze, row, col + 1) ||     // try right
                navigate(maze, row - 1, col) ||     // try up
                navigate(maze, row + 1, col)) {     // try down
            maze.mark(row, col);
            // System.out.println(maze);
            return true;
        }

        return false;
    }
}
