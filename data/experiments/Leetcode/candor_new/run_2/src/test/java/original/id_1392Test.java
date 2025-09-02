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
        String s = "ababa";
        int expected = 3;

        // Act
        Solution1392 solution1392 = new Solution1392();
        String actual = solution1392.longestPrefix(s);

        // Assert
        assertEquals(expected, actual.length());
    }
}
