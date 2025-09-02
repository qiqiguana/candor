package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3287.
*/
class Solution3287Test {
    @Test
    void testMaxValue() {
        Solution3287 solution = new Solution3287();
        int[] nums = {1, 2, 3, 4};
        int k = 2;
        int expected = 4;
        assertEquals(expected, solution.maxValue(nums, k));
    }
}