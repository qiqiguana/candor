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
    @Test
    public void test_circle_touches_left_and_top() {
        Solution3235 s = new Solution3235();
        int xCorner = 3;
        int yCorner = 4;
        int[][] circles = {{1, 2, 1}};
        assertTrue(s.canReachCorner(xCorner, yCorner, circles));
    }
    @Test
    public void test_canReachCorner_ReturnsTrue_WhenPathExists1() {
        Solution3235 solution = new Solution3235();
        int xCorner = 3;
        int yCorner = 4;
        int[][] circles = {{2, 2, 1}};
        boolean result = solution.canReachCorner(xCorner, yCorner, circles);
        assertTrue(result);
    }
    @Test
    public void test_CrossProductWithTwoCirclesTouchingCorner_1() {
        Solution3235 solution = new Solution3235();
        int xCorner = 3;
        int yCorner = 3;
        int[][] circles = {{1, 1, 1}, {2, 2, 1}};
        boolean result = solution.canReachCorner(xCorner, yCorner, circles);
        assertFalse(result);
    }
    @Test
    public void testCanReachCorner_MultipleIntersections() {
        Solution3235 s = new Solution3235();
        int xCorner = 4;
        int yCorner = 3;
        int[][] circles = {{1,2,1},{2,1,1}};
        boolean result = s.canReachCorner(xCorner, yCorner, circles);
        assertFalse(result);
    }
}