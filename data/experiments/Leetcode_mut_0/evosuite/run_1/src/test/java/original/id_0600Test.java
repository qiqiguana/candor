package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0600.
*/
class Solution0600Test {
    @Test
    void testFindIntegers_1() {
        // Arrange
        Solution0600 solution = new Solution0600();
        int n = 6; // binary: 110
        int expected = 5;

        // Act
        int actual = solution.findIntegers(n);

        // Assert
        assertEquals(expected, actual);
    }
}