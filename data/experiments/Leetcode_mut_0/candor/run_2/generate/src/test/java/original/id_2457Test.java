package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2457.
*/
class Solution2457Test {
    @Test
    void testMakeIntegerBeautiful() {
        Solution2457 solution = new Solution2457();
        long n = 16;
        int target = 6;
        long expected = 4;
        assertEquals(expected, solution.makeIntegerBeautiful(n, target));
    }
}