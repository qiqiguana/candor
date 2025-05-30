package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1846.
*/
class Solution1846Test {
    @Test
    void testMaximumElementAfterDecrementingAndRearranging() {
        // Arrange
        int[] arr = {2, 2, 1, 2, 1};
        Solution1846 solution1846 = new Solution1846();

        // Act
        int result = solution1846.maximumElementAfterDecrementingAndRearranging(arr);

        // Assert
        assertEquals(2, result);
    }
}