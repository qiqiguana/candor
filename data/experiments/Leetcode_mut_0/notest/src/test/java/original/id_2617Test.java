package original;

import java.util.PriorityQueue;

import java.util.Arrays;

import java.util.Queue;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2617.
*/
class Solution2617Test {

    @Test
    void testMinimumVisitedCells1() {
        Solution2617 solution = new Solution2617();
        int[][] grid = {{3,4,1},{3,2,1}};
        int expected = 3;
        int actual = solution.minimumVisitedCells(grid);
        assertEquals(expected, actual);
    }

}
