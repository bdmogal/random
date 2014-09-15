package random.algos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmogal on 9/14/14.
 */
public class LIS {

    public static String lis(int [] arr) {
        int [] L = new int[arr.length];
        String [] paths = new String[arr.length];
        int maxLength = 0;

        for (int i = 0; i < arr.length; i++) {
            L[i] = 1;
            paths[i] = arr[i] + " ";
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j] && L[i] < L[j] + 1) {
                    L[i] = 1 + L[j];
                    paths[i] = paths[j] + arr[i] + " ";
                    if (maxLength < L[i]) {
                        maxLength = L[i];
                    }
                }
                /*else {
                    L[i] = L[j];
                    paths[i] = paths[j];
                }*/
            }
        }

        for (int i = 1; i < paths.length; i++) {
            System.out.print(L[i] + " ");
            if (maxLength == L[i]) return paths[i];
        }

        return null;
    }

    private static void print(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
    }

    public static void main(String [] args) {
        int [] arr = {10, 22, 9, 33, 21, 50, 41, 60, 80 };
        String lis = lis(arr).trim();
        System.out.println("lis = " + lis);
        System.out.println("length = " + lis.split(" ").length);
    }
}
