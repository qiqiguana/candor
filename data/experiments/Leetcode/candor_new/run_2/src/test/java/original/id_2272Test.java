package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2272.
*/
class Solution2272Test {
    @Test
    void testLargestVariance_1() {
        // Arrange
        Solution2272 solution = new Solution2272();
        String s = "abcde";
        int expected = 0;
        // Act
        int actual = solution.largestVariance(s);
        // Assert
        assertEquals(expected, actual);
    }
}
