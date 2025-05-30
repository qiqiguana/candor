package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0780.
*/
class Solution0780Test {
    @Test
    void testReachingPoints_ReturnsTrue_WhenTargetReached2() {
        // Arrange
        Solution0780 solution = new Solution0780();
        int sx = 1;
        int sy = 1;
        int tx = 3;
        int ty = 5;

        // Act
        boolean result = solution.reachingPoints(sx, sy, tx, ty);

        // Assert
        assertTrue(result);
    }
}