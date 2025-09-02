package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2457.
*/
class Solution2457Test {
    @Test
    void makeIntegerBeautiful() {
        Solution2457 solution = new Solution2457();
        long result = solution.makeIntegerBeautiful(16, 6);
        assertEquals(4, result);
    }
}