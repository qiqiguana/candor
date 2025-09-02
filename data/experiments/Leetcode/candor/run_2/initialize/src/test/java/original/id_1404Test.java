package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1404.
*/
class Solution1404Test {
    @Test
    void testNumSteps_CarryIsTrue_ReturnsExpectedValue() {
        // Arrange
        Solution1404 solution = new Solution1404();
        String s = "1101";

        // Act
        int result = solution.numSteps(s);

        // Assert
        assertEquals(6, result);
    }
}