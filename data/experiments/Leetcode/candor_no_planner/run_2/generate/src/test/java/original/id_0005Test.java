package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0005.
*/
class Solution0005Test {
    @Test
    void testLongestPalindrome1() {
        // Arrange
        String input = "babad";
        String expected = "aba";
        Solution0005 solution = new Solution0005();
        
        // Act
        String actual = solution.longestPalindrome(input);
        
        // Assert
        assertEquals(expected, actual);
    }
}
