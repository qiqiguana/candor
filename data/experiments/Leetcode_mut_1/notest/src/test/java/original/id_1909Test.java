package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1909.
*/
class Solution1909Test {
    @Test
    void test_canBeIncreasing_ReturnsTrue_WhenArrayHasOneElement() {
        // Arrange
        Solution1909 solution = new Solution1909();
        int[] nums = {1};
        boolean expected = true;
        
        // Act
        boolean actual = solution.canBeIncreasing(nums);
        
        // Assert
        assertEquals(expected, actual);
    }
}