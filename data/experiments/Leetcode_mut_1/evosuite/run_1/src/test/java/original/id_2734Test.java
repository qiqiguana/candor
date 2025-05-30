package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2734.
*/
class Solution2734Test {
    @Test
    void testSmallestString_allACharacters_ReturnsZAtTheEnd() {
        // Arrange
        String input = "aaaa";
        Solution2734 solution = new Solution2734();
        String expectedOutput = "aaaz";

        // Act
        String output = solution.smallestString(input);

        // Assert
        assertEquals(expectedOutput, output);
    }
}