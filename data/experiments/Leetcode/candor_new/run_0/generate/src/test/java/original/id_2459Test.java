package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2459.
*/
class Solution2459Test {

@Test
void testSortArray_WhenValidInput_ReturnsMinimum()
{
    // Arrange
    int[] nums = {0, 1, 2};
    Solution2459 solution = new Solution2459();
    
    // Act
    int result = solution.sortArray(nums);
    
    // Assert
    assertEquals(0, result);
}
}