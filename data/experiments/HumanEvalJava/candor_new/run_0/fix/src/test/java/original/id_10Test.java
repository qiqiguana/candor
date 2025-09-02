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
    
    @Test
        public void testNothing(){
            MakePalindrome s = new MakePalindrome();
            }
    @Test
    public void testInitialization() {
    	// Given 
    	String input = "";
    	// When
    	String result = MakePalindrome.makePalindrome(input);
    	// Then
    	assertNotNull(result);
    }
    @Test
    public void testValidPalindromeReturnValue() {
        // Given 
        String input = "abcde";
        // When
        String result = MakePalindrome.makePalindrome(input);
        // Then
        assertEquals("abcdedcba", result);
    }
                                    
}