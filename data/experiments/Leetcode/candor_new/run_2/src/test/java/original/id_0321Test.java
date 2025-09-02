package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0321.
*/
class Solution0321Test {

    @Test
    void testMaxNumber() {
        Solution0321 solution = new Solution0321();
        int[] nums1 = {3, 4, 6, 5};
        int[] nums2 = {9, 1, 2, 5, 8, 3};
        int k = 5;
        int[] expected = {9, 8, 6, 5, 3};
        assertArrayEquals(expected, solution.maxNumber(nums1, nums2, k));
    }
}
