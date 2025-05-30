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
        Solution1035 solution = new Solution1035();
        int[] nums1 = {1, 3, 5, 7};
        int[] nums2 = {2, 4, 6, 8};

        // Act and Assert
        assertEquals(0, solution.maxUncrossedLines(nums1, nums2));
    }
}