package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digitsum.
*/
class DigitsumTest {
    @Test
    void testDigitSum_UpperCharactersOnly() {
        String input = "abAB";
        int expectedOutput = 131;
        assertEquals(expectedOutput, Digitsum.digitSum(input));
    }
    
    @Test
        public void testNothing(){
            Digitsum s = new Digitsum();
            }
    @Test
    public void testNonAlphabetCharacters2() {
        String input = "Hello World!";
        int expected_result = 72 + 87; // H and W are the only uppercase characters in the input string.
        assertEquals(expected_result, Digitsum.digitSum(input));
    }
                                    
}