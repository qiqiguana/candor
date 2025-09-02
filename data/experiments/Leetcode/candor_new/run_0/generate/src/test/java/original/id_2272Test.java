package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2272.
*/
class Solution2272Test {
    @Test
    void testLargestVariance() {
        Solution2272 solution = new Solution2272();
        String s = "abcabc";
        int expected = 1;
        int actual = solution.largestVariance(s);
        assertEquals(expected, actual);
    }
}