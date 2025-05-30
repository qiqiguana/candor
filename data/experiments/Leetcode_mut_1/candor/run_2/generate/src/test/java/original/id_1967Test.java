package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1967.
*/
class Solution1967Test {
    @Test
    void testNumOfStrings_withPatternExists_ReturnsCount() {
        // Arrange
        String[] patterns = {"a", "b"};
        String word = "ab";
        Solution1967 solution = new Solution1967();

        // Act
        int result = solution.numOfStrings(patterns, word);

        // Assert
        assertEquals(2, result);
    }
}