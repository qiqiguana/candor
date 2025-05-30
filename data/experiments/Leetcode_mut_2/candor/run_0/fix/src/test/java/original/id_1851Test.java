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
    void testMinInterval_SingleInterval() {
        int[][] intervals = {{1, 5}};
        int[] queries = {2, 3, 4};
        int[] expected = {5, 5, 5};
        Solution1851 solution = new Solution1851();
        assertArrayEquals(expected, solution.minInterval(intervals, queries));
    }
}