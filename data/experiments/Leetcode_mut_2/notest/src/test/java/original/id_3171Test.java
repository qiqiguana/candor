package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3171.
*/
class Solution3171Test {
    @Test
    void testMinimumDifference_Simple_2() {
        int[] nums = {1, 5, 7};
        int k = 10;
        Solution3171 solution = new Solution3171();
        assertEquals(3, solution.minimumDifference(nums, k));
    }
}
