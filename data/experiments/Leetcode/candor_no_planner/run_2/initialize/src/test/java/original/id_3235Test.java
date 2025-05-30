package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3235.
*/
class Solution3235Test {
    @Test
    void testCanReachCorner_CircleOutsideLeftTopCorner_ReturnsTrue() {
        // Arrange
        Solution3235 solution = new Solution3235();
        int xCorner = 10;
        int yCorner = 10;
        int[][] circles = {{15, 0, 3}, {0, 15, 3}};

        // Act
        boolean result = solution.canReachCorner(xCorner, yCorner, circles);

        // Assert
        assertTrue(result);
    }
}