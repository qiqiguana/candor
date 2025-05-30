package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1998.
*/
class Solution1998Test {
    @Test
    void testGcdSort_ReturnsTrue_ForSortedArray() {
        // Arrange
        int[] nums = {4, 2, 10};
        Solution1998 solution = new Solution1998();

        // Act and Assert
        assertTrue(solution.gcdSort(nums));
    }
}