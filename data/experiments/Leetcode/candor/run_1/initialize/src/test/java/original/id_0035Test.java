package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0035.
*/
class Solution0035Test {
    @Test
    void testSearchInsert_TargetExistsInArray_ReturnsIndex() {
        // Arrange
        Solution0035 solution = new Solution0035();
        int[] nums = {1, 3, 5, 6};
        int target = 5;

        // Act
        int result = solution.searchInsert(nums, target);

        // Assert
        assertEquals(2, result);
    }
}