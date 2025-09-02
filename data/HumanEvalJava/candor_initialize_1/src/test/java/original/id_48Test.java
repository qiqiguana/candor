package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPalindrome.
*/
class IsPalindromeTest {
    @Test
    void test_isPalindrome_with_empty_string() {
        // Given
        String text = "";

        // When
        boolean result = IsPalindrome.isPalindrome(text);

        // Then
        assertTrue(result);
    }
    @Test
    public void testEmptyStringPalindrome() {
        assertTrue(IsPalindrome.isPalindrome(""));
    }
    @Test
    public void testSingleCharacterPalindrome() {
        assertTrue(IsPalindrome.isPalindrome("a"));
    }
    @Test
    public void testLongerPalindrome() {
        assertTrue(IsPalindrome.isPalindrome("abba"));
    }
    @Test
    public void testNonPalindromeString() {
        assertFalse(IsPalindrome.isPalindrome("abcd"));
    }
    @Test
    public void testPalindromeWithDifferentCase() {
        assertFalse(IsPalindrome.isPalindrome("Abba"));
    }
    @Test
    public void testNullInputFixed() {
        assertThrows(NullPointerException.class, () -> IsPalindrome.isPalindrome(null));
    }
    @Test
    public void TestEmptyString() {
    	assertTrue(IsPalindrome.isPalindrome(""));
    }
    @Test
    public void TestSingleCharacter() {
    	assertTrue(IsPalindrome.isPalindrome("a"));
    }
    @Test
    public void TestPalindromeWithEvenLength1() {
    	assertTrue(IsPalindrome.isPalindrome("abba"));
    }
    @Test
    public void TestNotAPalindrome() {
    	assertFalse(IsPalindrome.isPalindrome("abcd"));
    }
    @Test
    public void TestPalindromeWithSpaces() {
    	assertTrue(IsPalindrome.isPalindrome("a b a"));
    }
    @Test
    public void TestNullInput() {
    	assertThrows(NullPointerException.class, () -> IsPalindrome.isPalindrome(null));
    }
    @Test
    public void testEmptyString() {
        assertTrue(IsPalindrome.isPalindrome(""));
    }
    @Test
    public void testSingleCharacter() {
        assertTrue(IsPalindrome.isPalindrome("a"));
    }
    @Test
    public void testTwoCharactersSame() {
        assertTrue(IsPalindrome.isPalindrome("aa"));
    }
    @Test
    public void testTwoCharactersDifferent() {
        assertFalse(IsPalindrome.isPalindrome("ab"));
    }
    @Test
    public void testLongStringPalindrome() {
        assertTrue(IsPalindrome.isPalindrome("abcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcba"));
    }
    @Test
    public void testLongStringNotPalindrome() {
        assertFalse(IsPalindrome.isPalindrome("abcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcx"));
    }
    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> IsPalindrome.isPalindrome(null));
    }
}