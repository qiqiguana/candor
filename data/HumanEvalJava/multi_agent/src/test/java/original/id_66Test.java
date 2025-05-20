package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digitsum.
*/
class DigitsumTest {

    @Test
    void testDigitSum_OnlyUpperCaseLetters() {
        String input = "AB";
        int expectedOutput = 131;
        int actualOutput = Digitsum.digitSum(input);
        assertEquals(expectedOutput, actualOutput);
    }
}