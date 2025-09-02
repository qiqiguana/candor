package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2862.
*/
class Solution2862Test {
    @Test
    void testMaximumSum_0()
    {
        // Arrange
        Solution2862 solution = new Solution2862();
        List<Integer> nums = List.of(1, 4, 9, 16, 25);
        long expected = 25;

        // Act
        long actual = solution.maximumSum(nums);

        // Assert
        assertEquals(expected, actual);
    }
}