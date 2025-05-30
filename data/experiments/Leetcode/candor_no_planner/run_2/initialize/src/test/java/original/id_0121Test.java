package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0121.
*/
class Solution0121Test {

    @Test
    void testMaxProfit() {
        Solution0121 solution = new Solution0121();
        int[] prices = {7, 1, 5, 3, 6, 4};
        assertEquals(5, solution.maxProfit(prices));
    }
}