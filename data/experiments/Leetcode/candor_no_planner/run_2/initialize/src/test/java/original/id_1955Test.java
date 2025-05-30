package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1955.
*/
class Solution1955Test {
    @Test
    void testCountSpecialSubsequences1() {
        // Arrange
        int[] nums = {0,1,0,2};
        Solution1955 solution = new Solution1955();
        int expected = 1;
        // Act
        int actual = solution.countSpecialSubsequences(nums);
        // Assert
        assertEquals(expected, actual);
    }
}
