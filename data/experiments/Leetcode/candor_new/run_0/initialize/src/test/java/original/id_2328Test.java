package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2328.
*/
class Solution2328Test {
    @Test
    void testCountPaths1()
    {
        // Arrange
        int[][] grid = {{1, 1}, {3, 4}};
        Solution2328 solution = new Solution2328();
        // Act and assert
        assertEquals(8, solution.countPaths(grid));
    }
}