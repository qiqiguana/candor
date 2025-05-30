package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3171.
*/
class Solution3171Test {
    @Test
    void testMinimumDifference_0() {
        // Arrange
        Solution3171 solution = new Solution3171();
        int[] nums = { 1, 5, 3, 19, 18, 25 };
        int k = 17;

        // Act
        int actual = solution.minimumDifference(nums, k);

        // Assert
        assertEquals(1, actual);
    }
}