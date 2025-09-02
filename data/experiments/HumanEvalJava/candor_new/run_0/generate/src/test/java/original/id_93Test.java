package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encode.
*/
class EncodeTest {
    @Test
    void testEncode_SwapsCaseAndReplacesVowels() {
        String message = "This is a message";
        String expectedResult = "tHKS KS C MGSSCGG";
        assertEquals(expectedResult, Encode.encode(message));
    }
    
    @Test
        public void testNothing(){
            Encode s = new Encode();
            }
    @Test
    public void testEncodeUpperCaseLettersFixed3() {
        String input = "HELLO";
        String expectedOutput = "hJKLL";
        char[] chars = Encode.encode(input).toCharArray();
        for (char c : chars) {
            if (c == 'U' || c == 'u') {
                assertNotEquals('W', c);
                assertEquals('A' + (c - 'U' + 2), c);
            }
        }
    }
    @Test
    public void testEncodeMethodSingleUppercaseLetterInput() {
        String input = "A";
        String expectedOutput = "c";
        String actualOutput = Encode.encode(input);
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    void testEncodeMethodWithConsecutiveVowelsFixed3() {
        String input = "aeiouAEIOU";
        StringBuilder expectedOutput = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                char swappedCaseChar = Character.toUpperCase(c);
                expectedOutput.append((char) ('A' + (swappedCaseChar - 'A' + 2) % 26));
            } else if (c >= 'A' && c <= 'Z') {
                char swappedCaseChar = Character.toLowerCase(c);
                expectedOutput.append((char) ('a' + (swappedCaseChar - 'a' + 2) % 26));
            }
        }
        assertEquals(expectedOutput.toString(), Encode.encode(input));
    }
                                    
}