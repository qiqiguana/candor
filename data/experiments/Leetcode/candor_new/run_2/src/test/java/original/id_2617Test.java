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
        int[][] grid = {{3,4,5},{1,2,10}};
        Solution2617 solution2617 = new Solution2617();
        assertEquals(solution2617.minimumVisitedCells(grid), 3);
    }
}
