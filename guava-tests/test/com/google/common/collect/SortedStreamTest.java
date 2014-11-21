package com.google.common.collect;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SortedStreamTest {

    @Test
    public void test() {
        List<Integer> x;
        List<Integer> y;
        SimpleSortedStream<Integer> xSS;
        SimpleSortedStream<Integer> ySS;

        // Same lengths
        x = Arrays.asList(1, 2, 3, 5, 8, 13, 21);
        y = Arrays.asList(2, 3, 5, 7, 11, 13, 17);

        xSS = new SimpleSortedStream<Integer>(x);
        ySS = new SimpleSortedStream<Integer>(y);
        assertEquals(Arrays.asList(2, 3, 5, 13), xSS.intersection(ySS).toList());

        xSS = new SimpleSortedStream<Integer>(x);
        ySS = new SimpleSortedStream<Integer>(y);
        assertEquals(Arrays.asList(1, 2, 3, 5, 7, 8, 11, 13, 17, 21), xSS.union(ySS).toList());

        // Different lengths
        x = Arrays.asList(2, 4, 6, 8);
        y = Arrays.asList(2, 3, 5, 7, 11);

        xSS = new SimpleSortedStream<Integer>(x);
        ySS = new SimpleSortedStream<Integer>(y);
        assertEquals(Arrays.asList(2), xSS.intersection(ySS).toList());

        xSS = new SimpleSortedStream<Integer>(x);
        ySS = new SimpleSortedStream<Integer>(y);
        assertEquals(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 11), xSS.union(ySS).toList());
    }

    @Test
    public void testIteration() {
        List<Integer> x = Arrays.asList(1, 2, 3, 5, 8, 13, 21);
        SimpleSortedStream<Integer> xSS = new SimpleSortedStream<Integer>(x);

        List<Integer> result = new ArrayList<Integer>();
        while (xSS.hasNext()) {
            result.add(xSS.next());
        }
        assertEquals(x, result);
    }
}
