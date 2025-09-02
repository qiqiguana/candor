package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0321.
*/
class Solution0321Test {
    @Test
    void testMaxNumber()
    {
        int[] nums1 = {3, 4, 6, 5};
        int[] nums2 = {9, 1, 2, 5, 8, 3};
        Solution0321 solution = new Solution0321();
        assertArrayEquals(new int[]{9, 8, 6, 5, 3}, solution.maxNumber(nums1, nums2, 5));
    }
}