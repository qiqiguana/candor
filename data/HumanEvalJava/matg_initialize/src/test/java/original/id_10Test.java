package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MakePalindrome.
*/
class MakePalindromeTest {
    @Test
    void testMakePalindromeEmptyString() {
        // Arrange and Act
        String result = MakePalindrome.makePalindrome("");
        // Assert
        assertEquals("", result);
    }
}