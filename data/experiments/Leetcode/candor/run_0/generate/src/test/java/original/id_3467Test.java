package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3467.
*/
class Solution3467Test {
    @Test
    void testTransformArrayEvenLength() {
        // Arrange
        int[] nums = {1, 2, 3, 4};
        Solution3467 solution = new Solution3467();
        int[] expected = {0, 0, 1, 1};
        // Act
        int[] result = solution.transformArray(nums);
        // Assert
        assertArrayEquals(expected, result);
    }
}