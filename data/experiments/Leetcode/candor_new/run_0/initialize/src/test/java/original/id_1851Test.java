package original;

import java.util.PriorityQueue;

import java.util.Arrays;

import java.util.Queue;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1851.
*/
class Solution1851Test {

    @Test
    void testMinDifference() {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.add(4);
        minHeap.add(2);
        minHeap.add(5);
        minHeap.add(3);
        minHeap.add(1);

        int result = 0;
        while (!minHeap.isEmpty()) {
            result += minHeap.poll();
        }

        assertEquals(result, 15);
    }
}