package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1895.
*/
class Solution1895Test {
    @Test
    void test_largestMagicSquare_3x4_grid() {
        // Arrange
        Solution1895 solution = new Solution1895();
        int[][] grid = {{4, 3, 8, 4},
                {9, 5, 1, 9},
                {2, 7, 6, 2}};
        // Act
        int result = solution.largestMagicSquare(grid);
        // Assert
        assertEquals(3, result);
    }
}