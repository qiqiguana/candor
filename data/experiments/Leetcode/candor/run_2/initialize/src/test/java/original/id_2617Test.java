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
    void testMinimumVisitedCells() {
        Solution2617 solution = new Solution2617();
        int[][] grid = {{3,4,2},{1,1,5}};
        assertEquals(3, solution.minimumVisitedCells(grid));
    }
}