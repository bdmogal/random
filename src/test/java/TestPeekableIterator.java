import random.algos.PeekableIteratorImpl;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by bmogal on 9/18/14.
 */
public class TestPeekableIterator {
    public static void main(String [] args) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(-4);
        list.add(3);
        list.add(43);
        list.add(-344);
        list.add(0);
        list.add(5);

        printList(list);

        Iterator<Integer> it = list.iterator();

        PeekableIteratorImpl<Integer> pIt = new PeekableIteratorImpl<Integer>(it);

        for (int i = 0; i < 10; i++) {
            System.out.println("Peek #" + (i + 1) + ": " + pIt.peek());
        }

        System.out.println("List after 10 peeks: ");
        printList(list);

        while (pIt.hasNext()) {
            Integer next = pIt.next();
            System.out.println("List after popping top (" + next + ") : ");
            printList(list);
        }
    }

    private static void printList(LinkedList<Integer> list) {
        System.out.print("[");
        for (Integer item : list) {
            System.out.print(item + ", ");
        }
        System.out.println("]");
    }
}
