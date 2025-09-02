package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1392.
*/
class Solution1392Test {
    @Test
    void testLongestPrefix_WhenInputIsAbab_ReturnsAb() {
        // Arrange
        Solution1392 solution = new Solution1392();
        String input = "abab";

        // Act
        String result = solution.longestPrefix(input);

        // Assert
        assertEquals("ab", result);
    }
}