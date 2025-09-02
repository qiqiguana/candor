package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2862.
*/
class Solution2862Test {
    @Test
    void testMaximumSum_SimpleCase_ReturnsExpectedResult() {
        // Arrange
        List<Integer> nums = List.of(1, 4, 9, 16, 25);
        Solution2862 solution = new Solution2862();

        // Act
        long result = solution.maximumSum(nums);

        // Assert
        assertEquals(25, result);
    }
}