package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2734.
*/
class Solution2734Test {
    @Test
    void testSmallestString_allAString_returnZAtTheEnd() {
        // Arrange
        String s = "aaaaa";
        Solution2734 solution = new Solution2734();
        // Act
        String result = solution.smallestString(s);
        // Assert
        assertEquals("aaaa" + "z", result);
    }
}