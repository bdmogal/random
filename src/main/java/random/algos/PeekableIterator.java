package random.algos;

import java.util.Iterator;

/**
 * Created by bmogal on 9/18/14.
 */
public interface PeekableIterator<E> extends Iterator<E> {
    public E peek();
}
