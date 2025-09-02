package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1102.
*/
class Solution1102Test {

    @Test
    void testMaximumMinimumPath() {
        // Arrange
        Solution1102 solution = new Solution1102();
        int[][] grid = {{1, 1}, {1, 0}};
        
        // Act
        int result = solution.maximumMinimumPath(grid);
        
        // Assert
        assertEquals(0, result);
    }
}