package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3171.
*/
class Solution3171Test {
    @Test
    void testMinimumDifference_Basic_2() {
        Solution3171 solution = new Solution3171();
        int[] nums = { 1, 5, 3, 19 };
        int k = 4;
        int expected = 1; // The smallest difference between the XOR of a subarray and 'k' is 1.
        assertEquals(expected, solution.minimumDifference(nums, k));
    }
}
