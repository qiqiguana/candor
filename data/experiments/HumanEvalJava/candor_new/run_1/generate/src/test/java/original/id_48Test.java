package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPalindrome.
*/
class IsPalindromeTest {

    @Test
    void test_isPalindrome_onEmptyString() {
        // Arrange & Act
        Boolean result = IsPalindrome.isPalindrome("");
        // Assert
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            IsPalindrome s = new IsPalindrome();
            }
    @Test
    public void testNonPalindromeWithDifferentCharactersAtStartAndEnd() {
        String text = "ab";
        assertFalse(IsPalindrome.isPalindrome(text));
    }
    @Test
    public void testPalindromeWithEvenLengthAndMiddleCharactersNotMatching() {
        String text = "abcdcba";
        assertTrue(IsPalindrome.isPalindrome(text));
    }
                                    
}