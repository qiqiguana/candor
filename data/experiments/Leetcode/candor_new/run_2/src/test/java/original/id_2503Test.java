package original;

import java.util.PriorityQueue;

import java.util.Arrays;

import java.util.Queue;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2503.
*/
class Solution2503Test {
    @Test
    void testMaxPoints() {
        Solution2503 solution = new Solution2503();
        int[][] grid = {{1, 2}, {3, 4}};
        int[] queries = {3, 5};
        int[] expected = {2, 4};
        assertArrayEquals(expected, solution.maxPoints(grid, queries));
    }
}