package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2206.
*/
class Solution2206Test {
    @Test
    void testDivideArray_OddOccurrence_ReturnsFalse() {
        Solution2206 solution = new Solution2206();
        int[] nums = {1, 2, 3};
        assertFalse(solution.divideArray(nums));
    }
}