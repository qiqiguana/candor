package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1400.
*/
class Solution1400Test {
    @Test
    void testCanConstruct_SimpleCase_ReturnsTrue() {
        // Arrange
        String s = "annabelle";
        int k = 2;
        Solution1400 solution = new Solution1400();

        // Act and Assert
        assertTrue(solution.canConstruct(s, k));
    }
}