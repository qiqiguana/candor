package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3082.
*/
class Solution3082Test {
    @Test
    void sumOfPower_0() {
        // Arrange
        Solution3082 solution = new Solution3082();
        int[] nums = {1, 2, 3};
        int k = 4;
        int expected = 2;
        // Act
        int actual = solution.sumOfPower(nums, k);
        // Assert
        assertEquals(expected, actual);
    }
}
