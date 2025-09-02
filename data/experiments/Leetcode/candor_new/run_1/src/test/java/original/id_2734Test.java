package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2734.
*/
class Solution2734Test {
    @Test
    void testSmallestString_WhenAllCharsAreA_ReturnsAZ() {
        // Arrange
        Solution2734 solution = new Solution2734();
        String s = "aaa";
        String expected = "aaz";
        // Act
        String result = solution.smallestString(s);
        // Assert
        assertEquals(expected, result);
    }
}