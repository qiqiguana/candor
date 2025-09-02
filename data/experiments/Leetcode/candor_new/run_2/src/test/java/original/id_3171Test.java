package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3171.
*/
class Solution3171Test {
    @Test
    void testMinimumDifference_SimpleCase() {
        Solution3171 solution = new Solution3171();
        int[] nums = { 2, 4, 8 };
        int k = 5;
        assertEquals(1, solution.minimumDifference(nums, k));
    }
}