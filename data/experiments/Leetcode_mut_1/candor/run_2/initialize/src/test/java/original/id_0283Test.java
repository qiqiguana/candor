package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0283.
*/
class Solution0283Test {
    @Test
    void test_moveZeroes_MoveNonZerosToTheFront() {
        // Arrange
        Solution0283 solution = new Solution0283();
        int[] nums = {0, 1, 0, 3, 12};
        int[] expected = {1, 3, 12, 0, 0};

        // Act
        solution.moveZeroes(nums);

        // Assert
        assertArrayEquals(expected, nums);
    }
}