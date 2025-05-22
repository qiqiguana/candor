package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPalindrome.
*/
class IsPalindromeTest {
    @Test
    void isPalindrome_shouldReturnTrueWhenEmptyString() {
        // Given
        String text = "";
        // When
        Boolean result = IsPalindrome.isPalindrome(text);
        // Then
        assertTrue(result);
    }
}