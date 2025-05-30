package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1998.
*/
class Solution1998Test {
    @Test
    void test_gcdSort_return_true_0() {
        Solution1998 solution = new Solution1998();
        int[] nums = {42, 22, 16, 15, 60};
        boolean result = solution.gcdSort(nums);
        assertTrue(result);
    }
}