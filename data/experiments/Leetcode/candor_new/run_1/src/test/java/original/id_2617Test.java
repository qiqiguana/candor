package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.PriorityQueue;
import java.util.Arrays;

/**
* Test class of Solution2617.
*/
class Solution2617Test {
    @Test
    void testMinimumVisitedCells() {
        // Arrange
        int[][] grid = {{3,4,5}, {3,2,6}};
        Solution2617 solution = new Solution2617();
        // Act and Assert
        assertEquals(3, solution.minimumVisitedCells(grid));
    }
}