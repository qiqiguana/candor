package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2928.
*/
class Solution2928Test {
    @Test
    void testDistributeCandies_WhenNIsGreaterThanThreeTimesLimit_ReturnsZero() {
        // Arrange
        Solution2928 solution = new Solution2928();
        int n = 10;
        int limit = 3;

        // Act and Assert
        assertEquals(0, solution.distributeCandies(n, limit));
    }
}
