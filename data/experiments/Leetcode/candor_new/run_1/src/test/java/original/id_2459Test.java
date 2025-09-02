package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2459.
*/
class Solution2459Test {
    @Test
    void testSortArrayShouldReturnMinimumCountOfSwaps() {
        // Arrange
        Solution2459 solution = new Solution2459();
        int[] nums = {3, 1, 0, 2};
        int expectedMinCountOfSwaps = 2;

        // Act
        int actualMinCountOfSwaps = solution.sortArray(nums);

        // Assert
        assertEquals(expectedMinCountOfSwaps, actualMinCountOfSwaps);
    }
}