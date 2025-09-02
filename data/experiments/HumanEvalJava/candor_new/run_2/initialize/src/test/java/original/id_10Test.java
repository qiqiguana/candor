package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MakePalindrome.
*/
class MakePalindromeTest {
    @Test
    void testMakePalindrome_EmptyString_ReturnsEmptyString() {
        String actual = MakePalindrome.makePalindrome("");
        assertEquals("", actual);
    }
}