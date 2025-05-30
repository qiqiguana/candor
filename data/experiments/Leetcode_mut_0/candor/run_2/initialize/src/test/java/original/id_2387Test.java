package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2387.
*/
class Solution2387Test {
    @Test
    void matrixMedian_3x3Grid_TargetIsMiddleElement() {
        // Arrange
        int[][] grid = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Solution2387 solution = new Solution2387();
        int expectedMedian = 5;

        // Act
        int actualMedian = solution.matrixMedian(grid);

        // Assert
        assertEquals(expectedMedian, actualMedian);
    }
}
