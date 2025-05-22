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
    
    @Test
        public void testNothing(){
            MakePalindrome s = new MakePalindrome();
            }
    @Test
    public void testMakePalindrome_EmptyString() {
        assertEquals("", MakePalindrome.makePalindrome(""));
    }
    @Test
    public void testMakePalindrome_SingleCharacter() {
        assertEquals("x", MakePalindrome.makePalindrome("x"));
    }
    @Test
    public void testMakePalindrome_ShortPalindrome() {
        assertEquals("xyx", MakePalindrome.makePalindrome("xyx"));
    }
    @Test
    public void testMakePalindrome_NonPalindrome() {
        assertEquals("xyzyx", MakePalindrome.makePalindrome("xyz"));
    }
    @Test
    public void testMakePalindrome_LongPalindrome() {
        assertEquals("catac", MakePalindrome.makePalindrome("cata"));
    }
    @Test
    public void testMakePalindrome_NullInput() {
        assertThrows(NullPointerException.class, () -> MakePalindrome.makePalindrome(null));
    }
    @Test
    public void testMakePalindrome_EdgeCase() {
        assertEquals("jerryrrej", MakePalindrome.makePalindrome("jerry"));
    }
    @Test
    public void testMakePalindrome_EmptyString_ReturnsEmptyString() {
    	String result = MakePalindrome.makePalindrome("");
    	assertEquals("", result);
    }
    @Test
    public void testMakePalindrome_SingleCharacter_ReturnsSameString() {
    	String result = MakePalindrome.makePalindrome("x");
    	assertEquals("x", result);
    }
    @Test
    public void testMakePalindrome_PalindromeString_ReturnsSameString() {
    	String result = MakePalindrome.makePalindrome("xyx");
    	assertEquals("xyx", result);
    }
    @Test
    public void testMakePalindrome_NonPalindromeString_ReturnsShortestPalindrome() {
    	String result = MakePalindrome.makePalindrome("xyz");
    	assertEquals("xyzyx", result);
    }
    @Test
    public void testMakePalindrome_LongString_ReturnsShortestPalindrome() {
    	String result = MakePalindrome.makePalindrome("jerry");
    	assertEquals("jerryrrej", result);
    }
    @Test
    public void testMakePalindrome_NullInput_ThrowsNullPointerException() {
    	assertThrows(NullPointerException.class, () -> MakePalindrome.makePalindrome(null));
    }
    @Test
    public void testMakePalindromeWithEmptyString() {
      assertEquals("", MakePalindrome.makePalindrome(""));
    }
    @Test
    public void testMakePalindromeWithSingleCharacter() {
      assertEquals("x", MakePalindrome.makePalindrome("x"));
    }
    @Test
    public void testMakePalindromeWithPalindromicString() {
      assertEquals("xyx", MakePalindrome.makePalindrome("xyx"));
    }
    @Test
    public void testMakePalindromeWithNonPalindromicString() {
      assertEquals("jerryrrej", MakePalindrome.makePalindrome("jerry"));
    }
    @Test
    public void testMakePalindromeWithNullInput() {
      assertThrows(NullPointerException.class, () -> MakePalindrome.makePalindrome(null));
    }
    @Test
    public void testMakePalindromeWithLongStringFixed() {
        String input = "abcdefghijklmnopqrstuvwxyz";
        String expected = "abcdefghijklmnopqrstuvwxyzyxwvutsrqponmlkjihgfedcba";
        String actual = MakePalindrome.makePalindrome(input);
        assertEquals(expected, actual);
    }
                                    
}