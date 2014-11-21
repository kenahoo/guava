package com.google.common.collect;

import java.util.Iterator;

/**
 * A SortedStream implementation that simply wraps an Iterator and makes sure that the elements in an iteration happen in strictly increasing order.
 */
public class SimpleSortedStream<T extends Comparable<T>> extends SortedStream<T> {

    private Iterator<T> it;
    private T prev = null;
    private boolean started = false;

    public SimpleSortedStream(Iterable<T> it) {
        this.it = it.iterator();
    }

    public SimpleSortedStream(Iterator<T> it) {
        this.it = it;
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public T next() {
        T next = it.next();
        if (started && prev.compareTo(next) >= 0)
            throw new IllegalArgumentException("Input to iterableIntersection() must be pre-sorted, with no duplicates; '" + prev + "' >= '" + next + "'");
        started = true;
        prev = next;
        return next;
    }
}
