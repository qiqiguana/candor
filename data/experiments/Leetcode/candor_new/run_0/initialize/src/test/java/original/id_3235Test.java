package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3235.
*/
class Solution3235Test {
    @Test
    void testCanReachCorner_CircleTouchingBothCorners_ReturnsFalse() {
        // Arrange
        int xCorner = 10;
        int yCorner = 10;
        int[][] circles = {{0, 0, 5}, {xCorner, yCorner, 5}};
        Solution3235 solution = new Solution3235();

        // Act
        boolean result = solution.canReachCorner(xCorner, yCorner, circles);

        // Assert
        assertFalse(result);
    }
}