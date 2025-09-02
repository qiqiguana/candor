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
        int[][] ops = {{2, 3}, {4, 5}};
        assertEquals(6, solution.maxCount(10, 10, ops));
    }
}
