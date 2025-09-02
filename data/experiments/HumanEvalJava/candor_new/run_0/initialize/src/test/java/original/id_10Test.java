package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MakePalindrome.
*/
class MakePalindromeTest {
    @Test
    void makePalindrome_singleCharacterString_returnSingleCharacterString() {
        String input = "x";
        String expectedOutput = "x";
        assertEquals(expectedOutput, MakePalindrome.makePalindrome(input));
    }
}