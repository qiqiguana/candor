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
    
    @Test
        public void testNothing(){
            Solution1851 s = new Solution1851();
            }
    @Test
    public void testMinIntervalNoOverlap1() {
        int[][] intervals = {{1, 2}, {3, 4}};
        int[] queries = {7};
        int[] expected = {-1};
        assertArrayEquals(expected, new Solution1851().minInterval(intervals, queries));
    }
    @Test
    public void testMinIntervalPartialOverlap1() {
        int[][] intervals = {{1, 3}, {4, 6}};
        int[] queries = {2};
        int[] expected = {3};
        assertArrayEquals(expected, new Solution1851().minInterval(intervals, queries));
    }
                                    
}