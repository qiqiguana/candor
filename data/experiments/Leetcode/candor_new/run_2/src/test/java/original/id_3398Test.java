package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3398.
*/
class Solution3398Test {
    @Test
    void testMinLength_0()
    {
        // Arrange
        Solution3398 solution = new Solution3398();
        String s = "1101001";
        int numOps = 2;
        int expected = 2;

        // Act
        int result = solution.minLength(s, numOps);

        // Assert
        assertEquals(expected, result);
    }
}