package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1901.
*/
class Solution1901Test {
    @Test
    void testFindPeakGrid_SimpleMatrix_ReturnsCorrectPosition() {
        // Arrange
        int[][] mat = {{1, 2}, {3, 4}};
        Solution1901 solution = new Solution1901();

        // Act
        int[] result = solution.findPeakGrid(mat);

        // Assert
        assertEquals(1, result[0]);
    }
}