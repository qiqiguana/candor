package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2268.
*/
class Solution2268Test {
    @Test
    void test_minimumKeypresses_SimpleCase() {
        // Arrange
        Solution2268 solution = new Solution2268();
        String s = "apple";
        int expected = 5;

        // Act
        int actual = solution.minimumKeypresses(s);

        // Assert
        assertEquals(expected, actual);
    }
}