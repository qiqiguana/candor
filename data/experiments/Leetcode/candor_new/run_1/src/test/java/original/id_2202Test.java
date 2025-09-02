package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2202.
*/
class Solution2202Test {
    @Test
    void test_maximumTop_kIsZero() {
        // Arrange
        Solution2202 solution = new Solution2202();
        int[] nums = {5, 4, 3, 2, 1};
        int k = 0;
        // Act and Assert
        assertEquals(nums[0], solution.maximumTop(nums, k));
    }
}