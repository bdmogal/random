package random.algos;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by bmogal on 9/18/14.
 */
public class PeekableIteratorImpl<E> implements PeekableIterator<E> {

    private Iterator<E> mIt = null;
    private E next = null;

    public PeekableIteratorImpl(Iterator<E> it) {
        mIt = it;
    }

    @Override
    public E peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if (next == null) {
            next = mIt.next();
        }

        return next;
    }

    @Override
    public boolean hasNext() {
        return mIt.hasNext();
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        // After this step 'toReturn' should point to the first element of the list
        E toReturn = peek();

        // remove top
        remove();

        return toReturn;
    }

    @Override
    public void remove() {
        // remove top
        mIt.remove();

        // reset null, so peek() will re-initialize 'next'
        next = null;
    }
}