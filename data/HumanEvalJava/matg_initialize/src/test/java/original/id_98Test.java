package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpper.
*/
class CountUpperTest {
    @Test
    void testCountUpper_EvenIndexUppercaseVowel_ReturnsExpectedValue() {
        String input = "aBCdEf";
        int expected = 1;
        int actual = CountUpper.countUpper(input);
        assertEquals(expected, actual);
    }
}