package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1400.
*/
class Solution1400Test {
    @Test
    void testCanConstruct_WhenStringLengthIsLessThanK_ReturnsFalse() {
        // Arrange
        String s = "abc";
        int k = 4;
        Solution1400 solution = new Solution1400();

        // Act and Assert
        assertFalse(solution.canConstruct(s, k));
    }
}