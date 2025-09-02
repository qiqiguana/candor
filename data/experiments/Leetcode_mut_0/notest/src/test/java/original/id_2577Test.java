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
    void testMinimumTime_ReturnMinusOne_WhenTopRightAndBottomLeftCellsAreBothGreaterThanOne() {
        int[][] grid = {{1, 2}, {3, 4}};
        Solution2577 solution = new Solution2577();
        assertEquals(-1, solution.minimumTime(grid));
    }
}