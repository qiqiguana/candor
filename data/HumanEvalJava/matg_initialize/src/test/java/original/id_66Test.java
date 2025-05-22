package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digitsum.
*/
class DigitsumTest {
    @Test
    void testDigitSum_withOnlyUppercaseLetters_shouldReturnCorrectSum() {
        String input = "AB";
        int expected = 131;
        int actual = Digitsum.digitSum(input);
        assertEquals(expected, actual);
    }
}