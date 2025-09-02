package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2907.
*/
class Solution2907Test {

@Test
void testMaxProfit_WithValidInput_ReturnsCorrectResult() {
    // Arrange
    int[] prices = {10, 20, 30};
    int[] profits = {1, 2, 3};
    Solution2907 solution = new Solution2907();

    // Act
    int result = solution.maxProfit(prices, profits);

    // Assert
    assertEquals(6, result);
}
}