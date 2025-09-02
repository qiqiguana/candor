package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digitsum.
*/
class DigitsumTest {
    @Test
    void testDigitSum_WhenStringHasUpperAndLowercaseLetters_ReturnsCorrectSum() {
        String input = "abAB";
        int expected = 131;
        assertEquals(expected, Digitsum.digitSum(input));
    }
}