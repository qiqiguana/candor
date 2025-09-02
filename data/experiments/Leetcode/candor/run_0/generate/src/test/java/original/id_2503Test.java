package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;

/**
* Test class of Solution2503.
*/
class Solution2503Test {
    @Test
    void testMaxPoints() {
        Solution2503 solution = new Solution2503();
        int[][] grid = {{1, 2}, {3, 4}};
        int[] queries = {1, 3, 5};
        int[] expected = {0, 2, 4};
        assertArrayEquals(expected, solution.maxPoints(grid, queries));
    }
}