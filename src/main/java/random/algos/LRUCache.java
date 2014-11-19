package random.algos;

import java.util.*;

/**
 * Created by bmogal on 9/18/14.
 */
public class LRUCache<K, V> {

    private static class DoublyLinkedList<T> {

        private static class DoublyLinkedListNode<T> {
            public DoublyLinkedListNode next;
            public DoublyLinkedListNode prev;
            public T data;

            public DoublyLinkedListNode(T pData) {
                data = pData;
                next = null;
                prev = null;
            }

            public DoublyLinkedListNode(DoublyLinkedListNode copy) {
                data = (T) copy.data;
                next = copy.next;
                prev = copy.prev;
            }
        }

        private DoublyLinkedListNode<T> head = null;

        public boolean isEmpty() {
            return size() == 0;
        }

        public int size() {
            if (head == null) return 0;
            int size = 0;
            DoublyLinkedListNode headCopy = new DoublyLinkedListNode(head);
            while (headCopy.next != null) {
                headCopy = headCopy.next;
                size++;
            }
            return size;
        }

        public DoublyLinkedListNode<T> getNodeAt(int pos) {
            if (pos < 0 || pos >= size()) {
                throw new IllegalArgumentException("Invalid pos - " + pos);
            }
            if (size() == 0) {
                throw new NoSuchElementException("list is empty");
            }
            int tPos = 0;
            DoublyLinkedListNode headCopy = head;
            while (headCopy.next != null) {
                if (tPos == pos) {
                    return headCopy;
                }
                headCopy = headCopy.next;
                tPos++;
            }
            throw new IllegalStateException("Invalid state - node must have been found by now.");
        }

        public DoublyLinkedListNode getNode(T item) {
            DoublyLinkedListNode headCopy = head;
            while (headCopy != null) {
                if (headCopy.data.equals(item)) {
                    return headCopy;
                }
                headCopy = headCopy.next;
            }
            throw new NoSuchElementException();
        }

        public void insertAt(T item, int pos) {
            DoublyLinkedListNode toInsertAfter = getNodeAt(pos);
            DoublyLinkedListNode toInsert = new DoublyLinkedListNode(item);
            DoublyLinkedListNode toInsertBefore = toInsertAfter.next;
            toInsertAfter.next = toInsert;
            toInsert.prev = toInsertAfter;
            toInsert.next = toInsertBefore;
        }

        public void insert(T item) {
            if (head == null) {
                head = new DoublyLinkedListNode<T>(item);
                return;
            }
            System.out.println("head=" + head.data);
            insertAt(item, size() - 1);
        }

        public void remove(DoublyLinkedListNode toRemove) {
            DoublyLinkedListNode toRemoveAfter = toRemove.prev;
            DoublyLinkedListNode toRemoveBefore = toRemove.next;
            toRemoveAfter.next = toRemoveBefore;
            toRemoveBefore.prev = toRemoveAfter;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            if (size() > 0) {
                DoublyLinkedListNode iterator = getNodeAt(0);
                while (iterator != null) {
                    sb.append(iterator.data);
                    sb.append(" <-> ");
                    iterator = iterator.next;
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    // storage for cache
    private Map<K, V> storage = null;

    // list to track recently used items
    private DoublyLinkedList<K> recents = null;

    public LRUCache() {
        storage = new HashMap<K, V>();
        recents = new DoublyLinkedList<K>();
    }

    private V get() {
        DoublyLinkedList.DoublyLinkedListNode head = recents.getNodeAt(0);
        V toReturn = storage.get(head);
        recents.remove(head);
        recents.insert((K) head.data);
        return toReturn;
    }

    private void put(K key, V value) {
        if (storage.containsKey(key)) {
            storage.put(key, value);
            DoublyLinkedList.DoublyLinkedListNode node = recents.getNode(key);
            if (node != null) {
                recents.remove(node);
            }
            recents.insert(key);
        }
        else {
            storage.put(key, value);
            recents.insert(key);
        }
    }

    public void printCacheOrder() {
        System.out.println(recents);
    }


    public static void main(String [] args) {
        LRUCache<Integer, String> cache = new LRUCache<Integer, String>();
        System.out.println("Adding 1");
        cache.put(1, "first");
        cache.printCacheOrder();
        System.out.println("Adding 2");
        cache.put(2, "second");
        cache.printCacheOrder();
        System.out.println("Adding 3");
        cache.put(3, "third");
        System.out.println("Adding 4");
        cache.put(4, "fourth");
        System.out.println("Adding 5");
        cache.put(5, "fifth");
        cache.printCacheOrder();
    }
}