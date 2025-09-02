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
        int[][] grid = {{3,4,5},{2,6,3}};
        assertEquals(3, solution.minimumVisitedCells(grid));
    }
    @Test
    public void testMinimumVisitedCellsValidPath() {
        Solution2617 s = new Solution2617();
        int[][] grid = {{3,4,2,1},{4,2,3,1},{2,1,0,0},{2,4,0,0}};
        assertEquals(4, s.minimumVisitedCells(grid));
    }
    @Test
    public void testRightwardMovementWithInvalidInput1() {
        Solution2617 solution = new Solution2617();
        int[][] grid = {{0,0},{0,0}};
        int expected = -1;
        assertEquals(expected, solution.minimumVisitedCells(grid));
    }
}