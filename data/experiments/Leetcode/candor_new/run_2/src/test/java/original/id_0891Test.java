package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0891.
*/
class Solution0891Test {
    @Test
    void testSumSubseqWidths() {
        Solution0891 solution = new Solution0891();
        int[] nums = {2, 3, 4, 5};
        int expected = 23;
        assertEquals(expected, solution.sumSubseqWidths(nums));
    }
}