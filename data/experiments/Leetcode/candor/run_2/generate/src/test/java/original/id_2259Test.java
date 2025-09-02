package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2259.
*/
class Solution2259Test {
    @Test
    void testRemoveDigit() {
        Solution2259 solution = new Solution2259();
        String number = "123";
        char digit = '2';
        String expected = "13";
        String actual = solution.removeDigit(number, digit);
        assertEquals(expected, actual);
    }
}