package random.algos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PermutationLists {
    static Iterator<List<String>> permute (List<List<String>> input) {
        List<List<String>> result = new ArrayList<List<String>>();
        doPermute(input, 0, new ArrayList<String>(), result);
        return result.iterator();
    }

    private static void doPermute(List<List<String>> input, int currentNum, List<String> accumulator, List<List<String>> result) {
        if (currentNum == input.size()) {
            print(accumulator);
            return;
        }

        for (String each : input.get(currentNum)) {
            accumulator.add(currentNum, each);
            // permute
            doPermute(input, currentNum + 1, accumulator, result);
            // make space for the next element in the permutation
            accumulator.remove(accumulator.size()-1);
        }
    }

    public static void main(String [] args) {
        List<List<String>> input = new ArrayList<List<String>>();
        input.add(Arrays.asList("a1", "a2", "a3"));
        input.add(Arrays.asList("b1", "b2"));
        input.add(Arrays.asList("c1", "c2", "c3", "c4"));

        permute(input);
    }

    private static void print(List<String> list) {
        for(int i = 0; i<list.size(); i++) {
            System.out.print(list.get(i));
            if (i < list.size() -1) {
                System.out.print(",");
            }
        }
        System.out.println();
    }
}
