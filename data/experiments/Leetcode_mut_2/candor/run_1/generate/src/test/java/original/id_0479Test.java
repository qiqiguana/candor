package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0479.
*/
class Solution0479Test {
    @Test
    void testLargestPalindrome_n_2_ReturnsMaxPalindrome() {
        // Arrange
        Solution0479 solution = new Solution0479();
        int n = 2;
        int expected = 987;
        
        // Act
        int actual = solution.largestPalindrome(n);
        
        // Assert
        assertEquals(expected, actual);
    }
}