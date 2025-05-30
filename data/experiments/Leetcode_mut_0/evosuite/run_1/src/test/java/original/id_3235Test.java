package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3235.
*/
class Solution3235Test {
    @Test
    void testCanReachCorner() {
        // Arrange
        Solution3235 solution = new Solution3235();
        int xCorner = 1;
        int yCorner = 2;
        int[][] circles = {{3,4,1}};

        // Act
        boolean result = solution.canReachCorner(xCorner, yCorner, circles);

        // Assert
        assertTrue(result);
    }
}