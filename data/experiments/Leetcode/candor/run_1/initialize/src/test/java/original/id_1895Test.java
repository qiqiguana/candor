package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1895.
*/
class Solution1895Test {
    @Test
    void testLargestMagicSquare1()
    {
        // Arrange
        int[][] grid = {{7,1,4},{3,12,6}};
        Solution1895 solution = new Solution1895();
        // Act
        int result = solution.largestMagicSquare(grid);
        // Assert
        assertEquals(1, result);
    }
}
