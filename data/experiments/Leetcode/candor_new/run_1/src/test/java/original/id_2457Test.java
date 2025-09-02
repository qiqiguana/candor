package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2457.
*/
class Solution2457Test {
    @Test
    void test_makeIntegerBeautiful() {
        Solution2457 solution = new Solution2457();
        long n = 16;
        int target = 6;
        long result = solution.makeIntegerBeautiful(n, target);
        assertEquals(4, result);
    }
}