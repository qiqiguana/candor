package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2549.
*/
class Solution2549Test {
    @Test
    void testDistinctIntegers_WhenInputIs2_Returns1() {
        // Arrange
        Solution2549 solution = new Solution2549();
        int input = 2;
        int expectedOutput = 1;

        // Act
        int actualOutput = solution.distinctIntegers(input);

        // Assert
        assertEquals(expectedOutput, actualOutput);
    }
}