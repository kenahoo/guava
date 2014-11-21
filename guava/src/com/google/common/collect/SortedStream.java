package com.google.common.collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A stream (possibly huge or infinite) where values iterate in strictly ascending order. Provides mechanisms for combining multiple such streams by
 * intersection or union.
 */
public abstract class SortedStream<T extends Comparable<T>> implements Iterator<T> {

    public SortedStream<T> intersection(SortedStream<T> s2) {
        return new IntersectionStream<T>(this, s2);
    }

    public SortedStream<T> union(SortedStream<T> s2) {
        return new UnionStream<T>(this, s2);
    }

    public static <S extends Comparable<S>> SortedStream<S> intersection(Iterable<SortedStream<S>> terms) {
        Iterator<SortedStream<S>> i = terms.iterator();
        if (!i.hasNext())
            return new SimpleSortedStream<S>(new ArrayList<S>());

        SortedStream<S> mainCursor = i.next();
        while (i.hasNext())
            mainCursor = mainCursor.intersection(i.next());

        return mainCursor;
    }

    public static <S extends Comparable<S>> SortedStream<S> union(Iterable<SortedStream<S>> terms) {
        Iterator<SortedStream<S>> i = terms.iterator();
        if (!i.hasNext())
            return new SimpleSortedStream<S>(new ArrayList<S>());

        SortedStream<S> mainCursor = i.next();
        while (i.hasNext())
            mainCursor = mainCursor.union(i.next());

        return mainCursor;
    }

    /**
     * Return entire stream as a List.
     */
    public List<T> toList() {
        List<T> out = new ArrayList<T>();
        while(hasNext())
            out.add(next());
        return out;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
