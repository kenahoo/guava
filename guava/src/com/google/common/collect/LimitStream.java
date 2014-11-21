package com.google.common.collect;

import java.util.NoSuchElementException;

/**
 * A SortedStream that imposes a maximum number of elements returned by its iteration.
 */
public class LimitStream<U extends Comparable<U>> extends SortedStream<U> {
    protected SortedStream<U> s;
    protected Integer limit;
    protected Integer seen = 0;

    public LimitStream(SortedStream<U> s, Integer limit) {
        this.s = s;
        this.limit = limit;
        this.seen = 0;
    }

    @Override
    public boolean hasNext() {
        return seen < limit && s.hasNext();
    }

    @Override
    public U next() {
        if (seen >= limit) {
            throw new NoSuchElementException("Iteration exceeded maximum of " + limit + " returned elements");
        }
        U foo = s.next();
        seen++;
        return foo;
    }
}
