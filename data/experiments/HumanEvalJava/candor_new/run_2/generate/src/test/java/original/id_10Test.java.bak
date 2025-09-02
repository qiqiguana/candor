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
    
    @Test
        public void testNothing(){
            MakePalindrome s = new MakePalindrome();
            }
    @Test
    public void testMakePalindrome_WithSingleCharacter_ReturnsSameCharacter() {
        // Arrange
        String input = "x";
        String expected = "x";
        // Act
        String result = MakePalindrome.makePalindrome(input);
        // Assert
        assertEquals(expected, result);
    }
    @Test
    public void testMakePalindrome_WithNonPalindromicString_ReturnsCorrectPalindrome() {
        // Arrange
        String input = "xyz";
        String expected = "xyzyx";
        // Act
        String result = MakePalindrome.makePalindrome(input);
        // Assert
        assertEquals(expected, result);
    }
                                    
}