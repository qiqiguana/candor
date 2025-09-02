package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2152.
*/
class Solution2152Test {
    @Test
    void testMinimumLines1() {
        // Arrange
        int[][] points = new int[][] {{3, 2}, {1, 4}, {-1, 2}};
        Solution2152 solution = new Solution2152();
        // Act and Assert
        assertEquals(2, solution.minimumLines(points));
    }
}