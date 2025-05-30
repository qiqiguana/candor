package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2272.
*/
class Solution2272Test {
    @Test
    void testLargestVariance1() {
        Solution2272 solution = new Solution2272();
        String s = "abcde";
        int expected = 0;
        assertEquals(expected, solution.largestVariance(s));
    }
}