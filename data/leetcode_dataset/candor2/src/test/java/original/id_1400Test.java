package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1400.
*/
class Solution1400Test {
    @Test
    void testCanConstruct_WhenInputStringLengthIsLessThanK_ThenReturnFalse() {
        // Arrange
        Solution1400 solution = new Solution1400();
        String input = "abc";
        int k = 4;
        boolean expectedResult = false;

        // Act
        boolean actualResult = solution.canConstruct(input, k);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}