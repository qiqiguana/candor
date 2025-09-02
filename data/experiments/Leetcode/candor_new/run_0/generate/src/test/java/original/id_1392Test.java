package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1392.
*/
class Solution1392Test {
    @Test
    void testLongestPrefix() {
        // Arrange
        Solution1392 solution = new Solution1392();
        String s = "abababa";

        // Act
        String result = solution.longestPrefix(s);

        // Assert
        assertEquals("ababa", result);
    }
    @Test
    public void testLongestPrefixWithNoHappyPrefix() {
        Solution1392 solution = new Solution1392();
        String input = "abcde";
        String expected = "";
        assertEquals(expected, solution.longestPrefix(input));
    }
}