package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2152.
*/
class Solution2152Test {
    @Test
    void testMinimumLines() {
        // Arrange
        int[][] points = new int[][] {{3, 2}, {-1, 4}};
        Solution2152 solution2152 = new Solution2152();
        // Act
        int actual = solution2152.minimumLines(points);
        // Assert
        assertEquals(1, actual);
    }
    @Test
    public void minimumLines_TwoLinesRequired() {
        Solution2152 s = new Solution2152();
        int result = s.minimumLines(new int[][] {{0,1},{2,3},{4,5},{4,3}});
        assertEquals(2, result);
    }
}