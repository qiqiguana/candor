package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2470.
*/
class Solution2470Test {

    @Test
    void test_subarrayLCM_should_return_3_when_k_is_6() {
        // Arrange
        int[] nums = {2, 3, 4};
        int k = 6;
        Solution2470 solution = new Solution2470();

        // Act
        int result = solution.subarrayLCM(nums, k);

        // Assert
        assertEquals(1, result);
    }
}