package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2202.
*/
class Solution2202Test {
    @Test
    void test_maximumTop_kIsZero() {
        Solution2202 solution = new Solution2202();
        int[] nums = {10, 20};
        assertEquals(10, solution.maximumTop(nums, 0));
    }
}