package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2470.
*/
class Solution2470Test {

    @Test
    void testSubarrayLCM_Simple_1() {
        // Arrange
        Solution2470 solution = new Solution2470();
        int[] nums = {2, 3, 4};
        int k = 12;

        // Act
        int result = solution.subarrayLCM(nums, k);

        // Assert
        assertEquals(2, result);
    }
}