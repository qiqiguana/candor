package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPalindrome.
*/
class IsPalindromeTest {
    @Test
    void testIsPalindrome_EmptyString_ReturnsTrue() {
        String text = "";
        Boolean result = IsPalindrome.isPalindrome(text);
        assertTrue(result);
    }
    
    @Test
     void testNothing(){
         IsPalindrome s = new IsPalindrome();
         }
    @Test
    public void testIsEmptyStringPalindrome() {
        assertTrue(IsPalindrome.isPalindrome(""));
    }
    @Test
    public void testIsSingleCharacterPalindrome() {
        assertTrue(IsPalindrome.isPalindrome("a"));
    }
    @Test
    public void testIsLongStringPalindrome() {
        assertTrue(IsPalindrome.isPalindrome("abcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcba"));
    }
    @Test
    public void testIsEvenLengthNonPalindrome() {
        assertFalse(IsPalindrome.isPalindrome("abcd"));
    }
    @Test
    public void testIsOddLengthNonPalindrome() {
        assertFalse(IsPalindrome.isPalindrome("abcde"));
    }
    @Test
    public void testIsNullInputThrowsException() {
        assertThrows(NullPointerException.class, () -> IsPalindrome.isPalindrome(null));
    }
                                  
}