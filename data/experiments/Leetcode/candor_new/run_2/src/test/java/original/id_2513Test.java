package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2513.
*/
class Solution2513Test {
    @Test
    void testMinimizeSet() {
        Solution2513 solution = new Solution2513();
        int divisor1 = 2, divisor2 = 7, uniqueCnt1 = 1, uniqueCnt2 = 4;
        assertEquals(5, solution.minimizeSet(divisor1, divisor2, uniqueCnt1, uniqueCnt2));
    }
}