package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2202.
*/
class Solution2202Test {
    @Test
    void testMaximumTop_ReturnsMaxNumberInFirstKMinusOneElements_WhenKIsLessThanN() {
        // Arrange
        Solution2202 solution = new Solution2202();
        int[] nums = {5, 3, 1, 6, 2};
        int k = 4;

        // Act
        int result = solution.maximumTop(nums, k);

        // Assert
        assertEquals(5, result);
    }
}