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
}