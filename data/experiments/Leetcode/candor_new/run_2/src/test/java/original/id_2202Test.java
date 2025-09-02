package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2202.
*/
class Solution2202Test {
    @Test
    void maximumTop_KIsZero_ReturnsFirstElement() {
        // Arrange
        int[] nums = {1, 2, 3};
        int k = 0;
        Solution2202 solution = new Solution2202();
        // Act and Assert
        assertEquals(nums[0], solution.maximumTop(nums, k));
    }
}