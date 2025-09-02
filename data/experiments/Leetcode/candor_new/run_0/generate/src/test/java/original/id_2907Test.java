package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2907.
*/
class Solution2907Test {
    @Test
    void testMaxProfit_WithValidInput_ReturnsExpectedResult() {
        // Arrange
        Solution2907 solution = new Solution2907();
        int[] prices = {1, 2, 3, 4, 5};
        int[] profits = {1, 2, 3, 4, 5};

        // Act
        int actual = solution.maxProfit(prices, profits);

        // Assert
        assertEquals(12, actual);
    }
    @Test
    public void testMaxProfit_EdgeCase_2() {
        Solution2907 s = new Solution2907();
        int[] prices = {10, 2, 3, 4};
        int[] profits = {100, 2, 7, 10};
        assertEquals(19, s.maxProfit(prices, profits));
    }
}