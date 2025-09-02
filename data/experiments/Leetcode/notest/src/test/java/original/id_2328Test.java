package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2328.
*/
class Solution2328Test {
    @Test
    void testCountPaths_WithSimpleGrid_ReturnsCorrectValue() {
        // Arrange
        Solution2328 solution = new Solution2328();
        int[][] grid = {{1, 2}, {3, 4}};

        // Act and Assert
        assertEquals(10, solution.countPaths(grid));
    }
}
