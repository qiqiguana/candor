package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1998.
*/
class Solution1998Test {
    @Test
    void testGcdSort() {
        // Arrange
        Solution1998 solution = new Solution1998();
        int[] nums = {7, 10, 5};

        // Act
        boolean result = solution.gcdSort(nums);

        // Assert
        assertFalse(result);
    }
}