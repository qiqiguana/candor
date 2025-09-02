package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1897.
*/
class Solution1897Test {
    @Test
    void testMakeEqual_DifferentWords_ReturnsFalse() {
        // Arrange
        Solution1897 solution = new Solution1897();
        String[] words = {"abc","cab","bca","dog"};

        // Act
        boolean result = solution.makeEqual(words);

        // Assert
        assertFalse(result);
    }
}