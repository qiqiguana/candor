package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1035.
*/
class Solution1035Test {
    @Test
    void testMaxUncrossedLines_WhenCalledWithTwoSortedArrays_ReturnsCorrectResult() {
        // Arrange
        int[] nums1 = {1, 4, 2};
        int[] nums2 = {1, 2, 4};
        Solution1035 solution = new Solution1035();
        int expected = 2;
        
        // Act
        int result = solution.maxUncrossedLines(nums1, nums2);
        
        // Assert
        assertEquals(expected, result);
    }
}