package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3082.
*/
class Solution3082Test {
    @Test
    void testSumOfPower() {
        Solution3082 solution = new Solution3082();
        int[] nums = {1, 2, 3};
        int k = 5;
        assertEquals(2, solution.sumOfPower(nums, k));
    }
}