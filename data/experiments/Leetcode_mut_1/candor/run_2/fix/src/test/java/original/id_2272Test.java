package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2272.
*/
class Solution2272Test {

    @Test
    void testLargestVariance_WhenInputIsEmptyString_ReturnsZero() {
        // Arrange
        Solution2272 solution = new Solution2272();
        String s = "";

        // Act
        int result = solution.largestVariance(s);

        // Assert
        assertEquals(0, result);
    }
}