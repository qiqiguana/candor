package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3287.
*/
class Solution3287Test {

    @Test
    void testMaxValueWithTwoElementsAndOneSwap() {
        // Arrange
        Solution3287 solution = new Solution3287();
        int[] nums = {1, 2};
        int k = 1;
        
        // Act
        int actual = solution.maxValue(nums, k);
        
        // Assert
        assertEquals(3, actual);
    }
}