package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2529.
*/
class Solution2529Test {
    @Test
    void maximumCount() {
        int[] nums = {1, -3, 2, 3, -5};
        Solution2529 solution2529 = new Solution2529();
        assertEquals(3, solution2529.maximumCount(nums));
    }
}
