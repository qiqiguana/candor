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
        // Arrange
        int[][] grid = {{0,1,2,3},{4,5,6,7}};
        Solution2577 solution = new Solution2577();
        
        // Act
        int actual = solution.minimumTime(grid);
        
        // Assert
        assertEquals(8, actual);
    }
}