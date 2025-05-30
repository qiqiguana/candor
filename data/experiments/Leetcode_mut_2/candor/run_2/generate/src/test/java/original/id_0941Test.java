package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0941.
*/
class Solution0941Test {
    @Test
    void test_validMountainArray_withValidInput_returnsTrue() {
        // Arrange
        Solution0941 solution = new Solution0941();
        int[] arr = {0, 3, 2, 1};
        boolean expected = true;

        // Act
        boolean actual = solution.validMountainArray(arr);

        // Assert
        assertEquals(expected, actual);
    }
}