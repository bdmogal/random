package random.algos;

import java.util.Scanner;

/**
 * Your algorithms have become so good at predicting the market that you now know what the share price of Wooden Orange Toothpicks Inc. (WOT) will be for the next N days.

 Each day, you can either buy one share of WOT, sell any number of shares of WOT that you own, or not make any transaction at all. What is the maximum profit you can obtain with an optimum trading strategy?

 Input

 The first line contains the number of test cases T. T test cases follow:

 The first line of each test case contains a number N. The next line contains N integers, denoting the predicted price of WOT shares for the next N days.

 Output

 Output T lines, containing the maximum profit which can be obtained for the corresponding test case.

 Constraints

 1 <= T <= 10
 1 <= N <= 50000

 All share prices are between 1 and 100000

 Sample Input

 3
 3
 5 3 2
 3
 1 2 100
 4
 1 3 1 2
 Sample Output

 0
 197
 3
 Explanation

 For the first case, you cannot obtain any profit because the share price never rises.
 For the second case, you can buy one share on the first two days, and sell both of them on the third day.
 For the third case, you can buy one share on day 1, sell one on day 2, buy one share on day 3, and sell one share on day 4.
 */


public class StockProfitMaximizer {

    private static int max(int [] prices, int start) {
        int max = prices[start];
        for (int i = start; i < prices.length; i++) {
            if (prices[i] > max) {
                max = prices[i];
            }
        }
        return max;
    }

    private static String string(int [] array, int start) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = start; i < array.length; i++) {
            sb.append(array[i]);
            if(i != array.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static void optimizeProfit(int [] stockPrices) {
        int start = 0;
        int profit = 0;

        while (start < stockPrices.length) {
            // get max price from start until the last day
            int max = max(stockPrices, start);

            // if max is on day 1 of current range, no need to calculate. profit = 0 for this range.
            if (stockPrices[start] == max) {
                start++;
                continue;
            }

            // retain start value
            int oldStart = start;

            // after this step 'start' will contain the day on which to first sell
            while (start < stockPrices.length && stockPrices[start] != max) {
                start++;
            }

            int cost = 0;
            // upto max, keep buying
            int j = oldStart;
            for (; j < start; j++) {
                cost += stockPrices[j];
            }

            // (start - oldStart) is the number of stocks we have bought so far, sell them all at price of day 'start'
            int earnings = (start - oldStart) * stockPrices[start];
            profit += (earnings - cost);

            // repeat from the next element onwards
            start++;
        }
        System.out.println(profit);
    }

    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        int numTestCases = in.nextInt();
        for (int i = 0; i < numTestCases; i++) {
            int numDays = in.nextInt();
            int [] stockPrices = new int[numDays];
            for (int j = 0; j < numDays; j++) {
                stockPrices[j] = in.nextInt();
            }
            optimizeProfit(stockPrices);
        }
    }
}