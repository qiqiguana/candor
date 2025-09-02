package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2907.
*/
class Solution2907Test {
    @Test
    void testMaxProfit() {
        int[] prices = {1, 2, 3, 4, 5};
        int[] profits = {1, 2, 3, 4, 5};
        Solution2907 solution = new Solution2907();
        assertEquals(12, solution.maxProfit(prices, profits));
    }
}