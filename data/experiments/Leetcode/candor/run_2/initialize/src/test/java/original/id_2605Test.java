package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2605.
*/
class Solution2605Test {
    @Test
    void testMinNumber() {
        Solution2605 solution = new Solution2605();
        int[] nums1 = {4, 10, 35};
        int[] nums2 = {3, 10, 5, 7, 22};
        assertEquals(10, solution.minNumber(nums1, nums2));
    }
}