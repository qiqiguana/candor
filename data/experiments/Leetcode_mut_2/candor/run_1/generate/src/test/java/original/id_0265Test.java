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
        int[][] costs = {{10, 20}, {30, 40}};
        int expected = 50;
        assertEquals(expected, solution.minCostII(costs));
    }

}
