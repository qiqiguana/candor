package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3235.
*/
class Solution3235Test {
    @Test
    void testCanReachCornerWhenCirclesDoNotIntersect() {
        // Arrange
        int xCorner = 10;
        int yCorner = 10;
        int[][] circles = new int[][]{
                {3, 4, 2},
                {5, 6, 1}
        };
        Solution3235 solution = new Solution3235();

        // Act and assert
        assertTrue(solution.canReachCorner(xCorner, yCorner, circles));
    }
}