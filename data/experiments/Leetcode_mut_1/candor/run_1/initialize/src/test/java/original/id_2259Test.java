package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2259.
*/
class Solution2259Test {
    @Test
    void removeDigit_oneOccurrence()
    {
        Solution2259 solution = new Solution2259();
        String number = "123";
        char digit = '1';
        String expected = "23";
        String actual = solution.removeDigit(number, digit);
        assertEquals(expected,actual);
    }
}