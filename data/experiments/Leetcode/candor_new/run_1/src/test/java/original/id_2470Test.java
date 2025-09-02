package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2470.
*/
class Solution2470Test {
    @Test
    void testSubarrayLCM_KFound() {
        Solution2470 solution = new Solution2470();
        int[] nums = {1, 2, 3};
        int k = 6;
        assertEquals(2, solution.subarrayLCM(nums, k));
    }
}