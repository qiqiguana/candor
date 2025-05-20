package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MakePalindrome.
*/
class MakePalindromeTest {
    @Test
    void testMakePalindrome_emptyString() {
        // Given an empty string
        String input = "";
        // When the makePalindrome method is called with this string
        String result = MakePalindrome.makePalindrome(input);
        // Then the returned string should be empty
        assertEquals("", result);
    }
}
