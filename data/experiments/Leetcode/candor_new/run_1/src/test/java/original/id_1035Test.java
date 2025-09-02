package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1035.
*/
class Solution1035Test {
    @Test
    void test_maxUncrossedLines() {
        Solution1035 solution = new Solution1035();
        int[] nums1 = {1, 4, 2};
        int[] nums2 = {1, 2, 4};
        assertEquals(2, solution.maxUncrossedLines(nums1, nums2));
    }
}