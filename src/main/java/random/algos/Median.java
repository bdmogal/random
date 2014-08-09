package random.algos;

import java.util.*;

/**
 The median of M numbers is defined as the middle number after sorting them in order, if M is odd. Or it is the average of the middle 2 numbers (again after sorting), if M is even. You have an empty number list at first. Then you can add in or remove some number from the list. For each add or remove operation, output the median of numbers in the list.

 Example :
 For a set of M = 5 numbers {9, 2, 8, 4, 1} the median is the third number in sorted set {1, 2, 4, 8, 9} which is 4. Similarly, for a set of M = 4, {5, 2, 10, 4}, the median is the average of second and the third element in the sorted set {2, 4, 5, 10} which is (4+5)/2 = 4.5.

 Input:
 The first line is an integer N that indicates the number of operations. Each of the next N lines is either a x or r x . a x indicates that x is added to the set, and r x indicates that x is removed from the set.

 Output:
 For each operation: If the operation is add output the median after adding x in a single line. If the operation is remove and the number x is not in the list, output Wrong! in a single line. If the operation is remove and the number x is in the list, output the median after deleting x in a single line. (If the result is an integer DO NOT output decimal point. And if the result is a real number , DO NOT output trailing 0s.)

 Note
 If your median is 3.0, print only 3. And if your median is 3.50, print only 3.5. Whenever you need to print the median and the list is empty, print Wrong!

 Constraints:
 0 < N <= 100,000
 For each a x or r x, x will always be an integer which will fit in 32 bit signed integer.

 Sample Input:

 7
 r 1
 a 1
 a 2
 a 1
 r 1
 r 2
 r 1
 Sample Output:

 Wrong!
 1
 1.5
 1
 1.5
 1
 Wrong!
 Note: As evident from the last line of the input, if after remove operation the list becomes empty, you have to print Wrong!.
 */
public class Median {
    static void print(String s) {
        System.out.println(s);
    }

    static String toString(int [] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length -1) sb.append(" ");
        }
        return sb.toString();
    }

    /* Head ends here*/
    static void median(String a[],int x[]) {
        int [] soFar = new int[0];
        for (int i = 0; i < a.length; i++) {
            int origLength = soFar.length;
            if("r".equals(a[i])) {
                soFar = remove(soFar, x[i]);
                if (soFar.length == origLength || soFar.length == 0) {
                    System.out.println("Wrong!");
                    continue;
                }
            }
            else if ("a".equals(a[i])) {
                soFar = add(soFar, x[i]);
            }
            else {
                System.err.println("bad!!");
                continue;
            }

            // sort
            sort(soFar);

            // find median
            System.out.println(pprint(median(soFar)));
        }
    }

    static String pprint(double num) {
        long whole = (long) num;
        double fraction = num - whole;
        if (fraction == 0) {
            return whole + "";
        }
        return num + "";
    }

    static void sort(int [] orig) {
        int [] helper = new int [orig.length];
        mergeSort(orig, helper, 0, orig.length - 1);
    }

    static void mergeSort (int [] arr, int [] helper, int left, int right) {
        if (left < right) {
            int center = (left + right)/2;
            mergeSort(arr, helper, left, center);
            mergeSort(arr, helper, center + 1, right);
            merge(arr, helper, left, center + 1, right);
        }
    }

    static void merge (int [] arr, int [] helper, int left, int center, int rightEnd) {
        int leftEnd = center - 1;
        int k = left;
        int right = center;
        int numElementsInIteration = rightEnd - left + 1;

        while (left <= leftEnd && right <= rightEnd) {
            if (arr[left] <= arr[right]) {
                helper[k++] = arr[left++];
            }
            else {
                helper[k++] = arr[right++];
            }
        }

        while (left <= leftEnd) {
            helper[k++] = arr[left++];
        }

        while (right <= rightEnd) {
            helper[k++] = arr[right++];
        }

        for (int i = 0; i < numElementsInIteration; i++, rightEnd--) {
            arr[rightEnd] = helper[rightEnd];
        }
    }


    static int [] add (int [] orig, int toAdd) {
        int [] added = Arrays.copyOf(orig, orig.length + 1);
        added[added.length - 1] = toAdd;
        return added;
    }

    static int [] remove(int [] orig, int toRemove) {
        int i = 0;
        boolean found = false;
        for (; i < orig.length; i++) {
            if (orig[i] == toRemove) {
                found = true;
                break;
            }
        }


        if(found) {
            int [] removed = new int[orig.length - 1];
            if (i == 0) {
                System.arraycopy(orig, 1, removed, 0, orig.length - 1);
            }
            else if (i == orig.length-1) {
                System.arraycopy(orig, 0, removed, 0, orig.length - 1);
            }
            else {
                System.arraycopy(orig, 0, removed, 0, i-1);
                System.arraycopy(orig, i+1, removed, i, orig.length - (i+1));
            }
            return removed;
        }
        else {
            return orig;
        }
    }

    static double median(int [] sorted) {
        int length = sorted.length;
        int medianIdx = length/2;
        double median;
        if (length % 2 == 0) {
            median = ( (double) (sorted[medianIdx] + sorted[medianIdx - 1]) )/2;
        }
        else {
            median = (double) sorted[medianIdx];
        }
        return median;
    }


    /* Tail starts here*/

    public static void main( String args[] ){


        Scanner in = new Scanner(System.in);

        int N;
        N = in.nextInt();

        String s[] = new String[N];
        int x[] = new int[N];

        for(int i=0; i<N; i++){
            s[i] = in.next();
            x[i] = in.nextInt();
        }

        median(s,x);
    }
}
