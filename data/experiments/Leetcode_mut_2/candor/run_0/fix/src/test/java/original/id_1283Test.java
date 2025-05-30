package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1283.
*/
class Solution1283Test {
    @Test
    void testSmallestDivisor() {
        Solution1283 solution = new Solution1283();
        int[] nums = {1, 2, 5};
        int threshold = 11;
        assertEquals(1, solution.smallestDivisor(nums, threshold));
    }
}
