package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2605.
*/
class Solution2605Test {
    @Test
    void testMinNumber_1() {
        int[] nums1 = {4, 2};
        int[] nums2 = {1, 10};
        int expected = 12;
        Solution2605 solution = new Solution2605();
        assertEquals(expected, solution.minNumber(nums1, nums2));
    }
}