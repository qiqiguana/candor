package original;

import java.util.HashSet;

import java.util.Set;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0548.
*/
class Solution0548Test {
    @Test
    void testSplitArray_1() {
        Solution0548 solution = new Solution0548();
        int[] nums = {3, 2, 2, 2, 5, 7, 3};
        assertFalse(solution.splitArray(nums));
    }
}
