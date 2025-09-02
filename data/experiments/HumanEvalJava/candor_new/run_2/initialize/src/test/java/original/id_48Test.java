package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPalindrome.
*/
class IsPalindromeTest {
    @Test
    void testIsPalindrome_PalindromeString_ReturnsTrue() {
        // Arrange
        String text = "aba";
        Boolean expected = true;
        
        // Act
        Boolean actual = IsPalindrome.isPalindrome(text);
        
        // Assert
        assertEquals(expected, actual);
    }
}