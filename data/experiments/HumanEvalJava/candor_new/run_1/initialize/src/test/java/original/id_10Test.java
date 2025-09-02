package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MakePalindrome.
*/
class MakePalindromeTest {
    @Test
    void testMakePalindromeWithEmptyString() {
        String result = MakePalindrome.makePalindrome("");
        assertEquals("", result);
    }
}