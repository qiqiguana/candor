package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0598.
*/
class Solution0598Test {
    @Test
    void testMaxCount() {
        Solution0598 solution = new Solution0598();
        int[][] ops = {{1, 2}, {3, 4}};
        int expected = 2;
        assertEquals(expected, solution.maxCount(5, 6, ops));
    }
}