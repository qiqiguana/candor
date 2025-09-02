package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2202.
*/
class Solution2202Test {
    @Test
    void test_maximumTop_withEvenK() {
        Solution2202 solution = new Solution2202();
        int[] nums = {10, 20, 30};
        int k = 2;
        assertEquals(30, solution.maximumTop(nums, k));
    }
}