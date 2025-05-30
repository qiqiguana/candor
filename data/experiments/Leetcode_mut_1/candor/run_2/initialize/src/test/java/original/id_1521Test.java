package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1521.
*/
class Solution1521Test {
    @Test
    void test_closestToTarget() {
        // Arrange
        Solution1521 solution = new Solution1521();
        int[] arr = {1000000, 1000000, 1000000};
        int target = 1;
        // Act and Assert
        assertEquals(999999, solution.closestToTarget(arr, target));
    }
}