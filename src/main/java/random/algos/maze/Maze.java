package random.algos.maze;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by bmogal on 9/14/14.
 */
public class Maze {

    private char [][] maze = null;
    private boolean [][] explored = null;

    private final int lastIsCol;
    private final int lastIsRow;
    private final boolean animated;

    public Maze (String filename) throws IOException {
        InputStream in = this.getClass().getResource(filename).openStream();
        Scanner scanner = new Scanner(in);
        String text = "";
        while (scanner.hasNextLine()) {
            text += scanner.nextLine();
            if (scanner.hasNextLine()) {
                text += "\n";
            }
        }
        String[] lines = text.split("[\r]?\n");
        maze = new char[lines.length][lines[0].length()];
        explored = new boolean[lines.length][lines[0].length()];

        // populate maze
        for (int row = 0; row < height(); row++) {
            if (lines[row].length() != width()) {
                throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was "
                        + lines[row].length() + " but should be " + width() + ")");
            }
            for (int col = 0; col < width(); col++) {
                maze[row][col] = lines[row].charAt(col);
            }
        }

        lastIsRow = -1;
        lastIsCol = -1;
        animated = false;
    }

    protected int width() {
        return maze[0].length;
    }

    protected int height() {
        return maze.length;
    }

    public String toString() {
        StringBuilder result = new StringBuilder(width() * (height() + 1));
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                if (row == lastIsRow && col == lastIsCol) {
                    result.append('?');
                } else if (maze[row][col] == '#') {
                    result.append('#');
                } else if (maze[row][col] == 'x') {
                    result.append('x');
                } else if (explored[row][col]) {
                    result.append('.');
                } else {
                    result.append(' ');
                }
            }
            result.append('\n');
        }
        maybePause();
        return result.toString();
    }

    private void maybePause() {
        if (animated) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    protected void mark(int row, int col) {
        maze[row][col] = 'x';
    }

    protected boolean isWall(int row, int col) {
        return maze[row][col] == '#';
    }

    protected boolean explored(int row, int col) {
        return explored[row][col];
    }

    protected void explore(int row, int col) {
        setExplored(row, col, true);
    }

    protected void unexplore(int row, int col) {
        setExplored(row, col, false);
    }

    private void setExplored(int row, int col, boolean isExplored) {
        explored[row][col] = isExplored;
    }
}
