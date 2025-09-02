package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2328.
*/
class Solution2328Test {

    @Test
    void testCountPaths() {
        // Arrange
        int[][] grid = {{1, 1}, {3, 4}};
        Solution2328 solution2328 = new Solution2328();
        int expected = 8;
        
        // Act
        int actual = solution2328.countPaths(grid);
        
        // Assert
        assertEquals(expected, actual);
    }
}
