package random.algos;

import java.util.Arrays;

/**
 * Created by bmogal on 9/17/14.
 */
public class PaintFill {

    private static int EMPTY = 0;
    private static int FILLED = 1;

    public static void main(String [] args) {
        int [][] flood = {
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,0,0,0,0,0,1,1},
                {1,1,0,0,0,0,0,1,1},
                {1,1,0,0,0,0,0,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1}
        };

        System.out.println("INPUT:");
        print(flood);
        System.out.println("FLOOD FILL:");
        floodfill(flood, 3, 4, 2);
        print(flood);
        int [][] edge = {
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,0,0,0,0,0,1,1},
                {1,1,0,0,0,0,0,1,1},
                {1,1,0,0,0,0,0,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1}
        };

        System.out.println("EDGE FILL:");
        edgefill(edge, 4, 3, 2);
        print(edge);
    }

    private static void print(int [][] toPrint) {
        for (int i = 0; i < toPrint.length; i++) {
            for (int j = 0; j < toPrint[0].length; j++) {
                System.out.print(toPrint[i][j]);
                if (j != toPrint[0].length) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static void floodfill(int[][] input, int x, int y, int color) {
        if (x < 0 || x >= input.length ||
                y < 0 || y >= input[0].length ||
                input[x][y] == FILLED) {
            return;
        }

        if (input[x][y] == EMPTY) {
            input[x][y] = color;
            floodfill(input, x - 1, y, color);
            floodfill(input, x + 1, y, color);
            floodfill(input, x, y - 1, color);
            floodfill(input, x, y + 1, color);
        }
    }

    private static void edgefill(int [][] input, int x, int y, int fill) {
        int bgcolor = input[x][y];
        boolean [][] mark = new boolean [input.length][input[0].length];
        edgefill(input, mark, x, y, bgcolor, fill);
    }

    private static void edgefill(int [][] input, boolean [][] mark, int x, int y, int boundary, int fill) {
        mark[x][y] = true;
        if (input[x][y] != boundary) {
            // crucial thing to remember in this if condition is that it returns from the method call if a non boundary color is found.
            // so, when it finds 1, it colors it as 2 and then returns.
            input[x][y] = fill;
            return;
        }

        if (x > 0 && !mark[x - 1][y]) edgefill(input, mark, x - 1, y, boundary, fill);
        if (x < input.length && !mark[x + 1][y]) edgefill(input, mark, x + 1, y, boundary, fill);
        if (y > 0 && !mark[x][y - 1]) edgefill(input, mark, x, y - 1, boundary, fill);
        if (y < input[0].length && !mark[x][y + 1]) edgefill(input, mark, x, y + 1, boundary, fill);
    }
}
