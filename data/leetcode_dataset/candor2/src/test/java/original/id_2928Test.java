package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2928.
*/
class Solution2928Test {
    @Test
    void testDistributeCandies_WhenNIsGreaterThan3TimesLimit_Returns0() {
        // Arrange
        Solution2928 solution = new Solution2928();
        int n = 10;
        int limit = 3;

        // Act
        int result = solution.distributeCandies(n, limit);

        // Assert
        assertEquals(0, result);
    }
}