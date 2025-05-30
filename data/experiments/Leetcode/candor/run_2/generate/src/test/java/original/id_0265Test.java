package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0265.
*/
class Solution0265Test {
    @Test
    void testMinCostII_0() {
        Solution0265 solution = new Solution0265();
        int[][] costs = {{1, 2, 3}, {4, 5, 6}};
        assertEquals(6, solution.minCostII(costs));
    }
}