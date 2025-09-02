package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3287.
*/
class Solution3287Test {
    @Test
    void test_maxValue() {
        Solution3287 solution = new Solution3287();
        int[] nums = {1, 2, 3, 4, 5};
        int k = 2;
        assertEquals(6, solution.maxValue(nums, k));
    }
}