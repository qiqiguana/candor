package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2529.
*/
class Solution2529Test {

    @Test
    void testMaximumCount() {
        Solution2529 solution = new Solution2529();
        int[] nums = {1, 2, -3, -4};
        assertEquals(2, solution.maximumCount(nums));
    }
}
