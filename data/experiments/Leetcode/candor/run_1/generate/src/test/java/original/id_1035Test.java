package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1035.
*/
class Solution1035Test {
    @Test
    void test_maxUncrossedLines_Simple() {
        // Arrange
        int[] nums1 = {1, 3, 4};
        int[] nums2 = {2, 3, 4};
        int expectedOutput = 2;
        Solution1035 solution = new Solution1035();
        
        // Act
        int actualOutput = solution.maxUncrossedLines(nums1, nums2);
        
        // Assert
        assertEquals(expectedOutput, actualOutput);
    }
}