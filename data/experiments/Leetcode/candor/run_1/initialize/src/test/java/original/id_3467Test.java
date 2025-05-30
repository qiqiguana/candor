package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3467.
*/
class Solution3467Test {
    @Test
    void testTransformArray_evenLength() {
        // Arrange
        Solution3467 solution = new Solution3467();
        int[] nums = {1, 2, 3, 4};

        // Act
        int[] result = solution.transformArray(nums);

        // Assert
        assertArrayEquals(new int[]{0, 0, 1, 1}, result);
    }
}