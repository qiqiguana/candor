package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPalindrome.
*/
class IsPalindromeTest {
    @Test
    void testIsPalindrome_ReturnsTrue_ForEmptyString() {
        // Arrange and Act
        boolean result = IsPalindrome.isPalindrome("");
        // Assert
        assertTrue(result);
    }
}