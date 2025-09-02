package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0005.
*/
class Solution0005Test {
    @Test
    void test_longestPalindrome() {
        // Arrange
        Solution0005 solution = new Solution0005();
        String input = "babad";
        String expectedOutput = "aba";
        
        // Act
        String actualOutput = solution.longestPalindrome(input);
        
        // Assert
        assertEquals(expectedOutput, actualOutput);
    }
}