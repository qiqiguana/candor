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
    void test_minimumTime() {
        Solution2577 solution = new Solution2577();
        int[][] grid = {{0,1,2,4},{2,3,5,6}, {1,3,2,3}};
        int expected = 7;
        assertEquals(expected, solution.minimumTime(grid));
    }

}
