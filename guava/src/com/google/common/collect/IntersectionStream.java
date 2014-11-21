package com.google.common.collect;


import java.util.NoSuchElementException;

/**
 * A SortedStream representing the intersection of two other SortedStreams.
 */
public class IntersectionStream<U extends Comparable<U>> extends SortedStream<U> {
    private SortedStream<U> s1;
    private SortedStream<U> s2;

    private Boolean hasNext = null;
    private U next;

    public IntersectionStream(SortedStream<U> s1, SortedStream<U> s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public boolean hasNext() {
        if (hasNext != null)
            return hasNext;

        // TODO I betcha this method can be simplified.  We're sort of doing a few things twice.

        if (!s1.hasNext() || !s2.hasNext()) {
            return hasNext = false;
        }

        U cur1 = s1.next();
        U cur2 = s2.next();

        if (cur1.compareTo(cur2)==0) {
            next = cur1;
            return hasNext = true;
        }

        while (s1.hasNext() && s2.hasNext()) {
            if (cur1.compareTo(cur2) < 0) {
                cur1 = s1.next();
            } else if (cur1.compareTo(cur2) > 0) {
                cur2 = s2.next();
            } else {
                next = cur1;
                return hasNext = true;
            }
        }

        return hasNext = false;
    }

    @Override
    public U next() {
        if (hasNext==null)
            hasNext();
        if (!hasNext)
            throw new NoSuchElementException();
        hasNext = null;

        return next;
    }
}
