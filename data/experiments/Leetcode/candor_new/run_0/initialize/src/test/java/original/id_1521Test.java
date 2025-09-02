package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1521.
*/
class Solution1521Test {
    @Test
    void testClosestToTarget() {
        Solution1521 solution = new Solution1521();
        int[] arr = {1000000, 1000000, 1000000};
        int target = 1;
        int expected = 999999;
        assertEquals(expected, solution.closestToTarget(arr, target));
    }
}