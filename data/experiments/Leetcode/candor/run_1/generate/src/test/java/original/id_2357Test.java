package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
/**
* Test class of Solution2357.
*/
class Solution2357Test {
    @Test
    void testMinimumOperations() {
        Solution2357 solution = new Solution2357();
        int[] nums = {1, 2, 3, 4};
        assertEquals(4, solution.minimumOperations(nums));
    }
}