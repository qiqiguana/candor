package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2202.
*/
class Solution2202Test {
    @Test
    void test_maximumTop_kIsZero() {
        // Arrange
        Solution2202 solution = new Solution2202();
        int[] nums = {1, 2, 3};
        int k = 0;

        // Act
        int actual = solution.maximumTop(nums, k);

        // Assert
        assertEquals(1, actual);
    }
}