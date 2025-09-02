package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0670.
*/
class Solution0670Test {
    @Test
    void testMaximumSwap_1() {
        Solution0670 solution = new Solution0670();
        int actual = solution.maximumSwap(2736);
        int expected = 7236;
        assertEquals(expected, actual);
    }
}