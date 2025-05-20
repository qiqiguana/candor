package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Digits.
*/
class DigitsTest {
    @Test
    void testDigits_ProductOfOddDigits() {
        int result = Digits.digits(235);
        assertEquals(15, result);
    }
}