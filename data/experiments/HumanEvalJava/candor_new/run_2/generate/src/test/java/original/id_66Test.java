package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digitsum.
*/
class DigitsumTest {
    @Test
    void testDigitSumUpperCharactersOnly() {
        String input = "abAB";
        int expectedOutput = 131;
        assertEquals(expectedOutput, Digitsum.digitSum(input));
    }
}