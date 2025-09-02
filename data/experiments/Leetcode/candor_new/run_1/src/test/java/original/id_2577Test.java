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
    void testMinimumTime_ReturnsMinusOne_WhenNoPathExists() {
        // Arrange
        int[][] grid = {{1,2},{2,1}};
        Solution2577 solution = new Solution2577();

        // Act and Assert
        assertEquals(-1, solution.minimumTime(grid));
    }
}