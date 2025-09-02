package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0479.
*/
class Solution0479Test {
    @Test
    void testLargestPalindrome_WhenNIsOne_ReturnsNine() {
        // Arrange
        Solution0479 solution = new Solution0479();
        int n = 1;
        int expected = 9;
        // Act
        int actual = solution.largestPalindrome(n);
        // Assert
        assertEquals(expected, actual);
    }
}