package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1067.
*/
class Solution1067Test {

    @Test
    void testDigitsCount() {
        Solution1067 solution = new Solution1067();
        int d = 3, low = 100, high = 999;
        int expected = 280;
        assertEquals(expected, solution.digitsCount(d, low, high));
    }
}
