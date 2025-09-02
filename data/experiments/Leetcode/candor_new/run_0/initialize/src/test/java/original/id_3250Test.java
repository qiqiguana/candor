package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3250.
*/
class Solution3250Test {
    @Test
    void testCountOfPairs_WhenCalled_ReturnsCorrectResult() {
        // Arrange
        int[] nums = {1, 2, 3};
        Solution3250 solution = new Solution3250();
        // Act
        int result = solution.countOfPairs(nums);
        // Assert
        assertEquals(4, result);
    }
}
