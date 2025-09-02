package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0265.
*/
class Solution0265Test {
    @Test
    void testMinCostII() {
        Solution0265 solution = new Solution0265();
        int[][] costs = {{1,2,3},{4,5,6}};
        int expected = 6;
        assertEquals(expected, solution.minCostII(costs));
    }
}