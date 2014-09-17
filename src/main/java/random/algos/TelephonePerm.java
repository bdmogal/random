package random.algos;

import java.util.Arrays;

/**
 * Created by bmogal on 9/16/14.
 */
public class TelephonePerm {

    private static String [] telchars = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqr", "tuv", "xyz"};

    private static void permute(String toPermute, int permLength) {
        boolean [] used = new boolean [toPermute.length()];
        Arrays.fill(used, false);
        permute(new StringBuilder(), toPermute, used, permLength);
    }

    private static void permute(StringBuilder permutedSoFar, String toPermute, boolean [] used, int permutationLength) {
        if (permutedSoFar.length() == permutationLength) {
            // done with this permutation
            System.out.println(permutedSoFar.toString());
            return;
        }

        for (int i = 0; i < toPermute.length(); i++) {
            if (used[i]) continue;
            permutedSoFar.append(toPermute.charAt(i));
            used[i] = true;
            permute(permutedSoFar, toPermute, used, permutationLength);
            used[i] = false;
            permutedSoFar.setLength(permutedSoFar.length() - 1);
        }
    }

    private static void combine(String str, int length) {
        doCombine(str, new StringBuilder(), 0, length);
    }

    private static void doCombine(String in, StringBuilder soFar, int start, int cLength) {
        int length = in.length();

        for (int i = start; i < length; i++) {
            soFar.append(in.charAt(i));
            if (soFar.length() == cLength) {
                System.out.println(soFar);
            }
            if (i < length - 1) {
                doCombine(in, soFar, i + 1, cLength);
            }

            soFar.setLength(soFar.length() - 1);
        }
    }

    private static void permuteTelephoneNum(String telephoneNumber) {
        // permute(getAllChars(telephoneNumber), telephoneNumber.length());
        System.out.println("----------RECURSIVE-----------");
        permuteRecursive(telephoneNumber);
        System.out.println();
        System.out.println("----------ITERATIVE-----------");
        permuteIterative(telephoneNumber);
    }

    private static String getAllChars(String phoneNumber) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < phoneNumber.length(); i++) {
            out.append(telchars[Character.getNumericValue(phoneNumber.charAt(i))]);
        }
        return out.toString();
    }

    private static char getCharAt(int digit, int pos) {
        return telchars[digit].charAt(pos);
    }

    private static void permuteRecursive(String phoneNumber) {
        char [] result = new char [phoneNumber.length()];
        doPermuteRecursive(phoneNumber, 0, result);
    }

    private static void doPermuteRecursive(String phoneNumber, int currentIndex, char [] result) {
        if (currentIndex == phoneNumber.length()) {
            System.out.println(new String(result));
            return;
        }

        for (int i = 0; i < 3; i++) {
            result[currentIndex] = getCharAt(Character.getNumericValue(phoneNumber.charAt(currentIndex)), i);
            doPermuteRecursive(phoneNumber, currentIndex + 1, result);
            if (phoneNumber.charAt(currentIndex) == 0 ||
                    phoneNumber.charAt(currentIndex) == 1) {
                return;
            }
        }
    }

    private static void permuteIterative(String phoneNumber) {
        int PHONE_NUMBER_LENGTH = phoneNumber.length();
        char [] result = new char [PHONE_NUMBER_LENGTH];
        // initialize
        for (int i = 0; i < PHONE_NUMBER_LENGTH; i++) {
            result[i] = getCharAt(Character.getNumericValue(phoneNumber.charAt(i)), 0);
        }

        while (true) {
            for (int i = 0; i < PHONE_NUMBER_LENGTH; i++) {
                System.out.print(result[i]);
            }
            System.out.println();

            for (int i = PHONE_NUMBER_LENGTH - 1; i >= -1; i--) {
                if (i == -1) return;        // traversed through the full number. done with the leftmost digit

                int currentDigit = Character.getNumericValue(phoneNumber.charAt(i));
                if (result[i] == getCharAt(currentDigit, 2) ||
                        currentDigit == 0 || currentDigit == 1) {
                    result[i] = getCharAt(currentDigit, 0);
                }
                else if (result[i] == getCharAt(currentDigit, 1)) {
                    result[i] = getCharAt(currentDigit, 2);
                    break;
                }
                else if (result[i] == getCharAt(currentDigit, 0)) {
                    result[i] = getCharAt(currentDigit, 1);
                    break;
                }
            }
        }
    }

    public static void main (String [] args) {
        System.out.println("-------------PERMUTATIONS-------------");
        permute("abc", 2);
        System.out.println("-------------COMBINATIONS-------------");
        combine("abc", 2);
        permuteTelephoneNum("587");
    }
}
