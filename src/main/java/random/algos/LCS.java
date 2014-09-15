package random.algos;

/**
 * Created by bmogal on 9/14/14.
 */
public class LCS {
    public static String recursiveLCS(CharSequence cs1, CharSequence cs2) {
        if (cs1.equals("") || cs2.equals("")) return "";
        else if (cs1.charAt(0) == cs2.charAt(0)) return cs1.charAt(0) + recursiveLCS(cs1.subSequence(1, cs1.length()), cs2.subSequence(1, cs2.length()));
        else {
            String x = recursiveLCS(cs1.subSequence(1, cs1.length()), cs2);
            String y = recursiveLCS(cs1, cs2.subSequence(1, cs2.length()));
            return x.length() > y.length() ? x : y;
        }
    }

    public static String dpLCS(CharSequence cs1, CharSequence cs2) {
        int [][] memoizer = new int[cs1.length()+1][cs2.length()+1];
        for (int i = cs1.length(); i >= 0; i--) {
            for (int j = cs2.length(); j >= 0; j--) {
                if (i == cs1.length() || j == cs2.length()) memoizer[i][j] = 0;
                else if (cs1.charAt(i) == cs2.charAt(j)) memoizer[i][j] = 1 + memoizer[i+1][j+1];
                else memoizer[i][j] = Math.max(memoizer[i+1][j], memoizer[i][j+1]);
            }
        }

        //printMemoizer(memoizer, cs1, cs2);
        int i = 0, j = 0;
        StringBuilder output = new StringBuilder();
        while (i < cs1.length() && j < cs2.length()) {
            if (cs1.charAt(i) == cs2.charAt(j)) {
                output.append(cs1.charAt(i));
                i++;
                j++;
            }
            else if (memoizer[i+1][j] > memoizer [i][j+1]) {
                i++;
            }
            else j++;
        }
        return output.toString();
    }

    public static void printMemoizer(int [][] mem, CharSequence cs1, CharSequence cs2) {
        for (int i = 0; i < cs1.length()+1; i++) {
            for (int j = 0; j < cs2.length()+1; j++) {
                if (i == 0) System.out.print(cs1.charAt(j) + " ");
                else if (j == 0) System.out.print(cs2.charAt(i) + " ");
                else System.out.print(mem[i-1][j-1] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String [] args) {
        String s1 = "nematode knowledge";
        String s2 = "empty bottle";
        System.out.println("***** Recursive *****");
        String rlcs = recursiveLCS(s1, s2);
        System.out.println("LCS = " + rlcs);
        System.out.println("LCS Length = " + rlcs.length());
        System.out.println();
        System.out.println("******** DP *********");
        String dplcs = dpLCS(s1, s2);
        System.out.println("LCS = " + dplcs);
        System.out.println("LCS length = " + dplcs.length());
    }
}
