package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1035.
*/
class Solution1035Test {
    @Test
    void testMaxUncrossedLines() {
        // Arrange
        int[] nums1 = {1, 3, 5, 7};
        int[] nums2 = {2, 3, 5, 8};
        Solution1035 solution1035 = new Solution1035();
        // Act and Assert
        assertEquals(2, solution1035.maxUncrossedLines(nums1, nums2));
    }
}