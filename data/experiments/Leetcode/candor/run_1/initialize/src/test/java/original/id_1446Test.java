package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1446.
*/
class Solution1446Test {
    @Test
    void testMaxPower() {
        Solution1446 solution = new Solution1446();
        String s = "leetcode";
        int expected = 2;
        assertEquals(expected, solution.maxPower(s));
    }
}
