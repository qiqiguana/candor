package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2206.
*/
class Solution2206Test {

    @Test
    void testDivideArray_EvenCounts_ReturnsTrue() {
        Solution2206 solution = new Solution2206();
        int[] nums = {3, 2, 3, 2, 4, 4};
        assertTrue(solution.divideArray(nums));
    }
}