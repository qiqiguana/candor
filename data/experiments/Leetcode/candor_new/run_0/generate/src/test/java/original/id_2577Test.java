package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2577.
*/
class Solution2577Test {
    @Test
    void testMinimumTime() {
        Solution2577 solution = new Solution2577();
        int[][] grid = {{0,1,3,2},{5,4,2,1},{6,8,3,1}};
        assertEquals(7, solution.minimumTime(grid));
    }
    @Test public void test_minimum_time_unreachable() { Solution2577 s = new Solution2577(); int[][] grid = {{0, 2, 4}, {3, 2, 1}, {1, 0, 4}}; assertEquals(-1, s.minimumTime(grid)); }
}