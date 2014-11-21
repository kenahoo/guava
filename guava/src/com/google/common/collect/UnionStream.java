package com.google.common.collect;


import java.util.NoSuchElementException;

/**
 * A SortedStream representing the union of two other SortedStreams.
 */
public class UnionStream<U extends Comparable<U>> extends SortedStream<U> {
    private PeekingIterator<U> s1;
    private PeekingIterator<U> s2;

    private Boolean hasNext = null;
    private U next;

    public UnionStream(SortedStream<U> s1, SortedStream<U> s2) {
        this.s1 = Iterators.peekingIterator(s1);
        this.s2 = Iterators.peekingIterator(s2);
    }

    @Override
    public boolean hasNext() {
        if (hasNext != null)
            return hasNext;

        if (!s1.hasNext() && !s2.hasNext()) {
            return hasNext = false;
        }
        if (!s1.hasNext()) {
            next = s2.next();
            return hasNext = true;
        }
        if (!s2.hasNext()) {
            next = s1.next();
            return hasNext = true;
        }

        // Now we know they both haveNext()

        U cur1 = s1.peek();
        U cur2 = s2.peek();

        if (cur1.compareTo(cur2) < 0) {
            next = s1.next();
            return hasNext = true;
        }
        if (cur1.compareTo(cur2) > 0) {
            next = s2.next();
            return hasNext = true;
        }

        // They're equal
        next = s1.next();
        s2.next();  // Throw away
        return hasNext = true;
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