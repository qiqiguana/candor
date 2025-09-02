package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2864.
*/
class Solution2864Test {
    @Test
    void testMaximumOddBinaryNumber_ReturnsCorrectResult() {
        // Arrange
        Solution2864 solution = new Solution2864();
        String input = "110";
        String expected = "101";

        // Act
        String result = solution.maximumOddBinaryNumber(input);

        // Assert
        assertEquals(expected, result);
    }
}