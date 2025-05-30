package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2907.
*/
class Solution2907Test {
	@Test
	void testMaxProfit() {
		Solution2907 solution = new Solution2907();
		int[] prices = {1, 2, 3, 4, 5};
		int[] profits = {1, 2, 3, 4, 5};
		int expected = 12;
		int actual = solution.maxProfit(prices, profits);
		assertEquals(expected, actual);
	}
}