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
    void testSplitArray() {
        Solution0548 solution = new Solution0548();
        int[] nums = {7, 2, 5, 10, 8};
        boolean actual = solution.splitArray(nums);
        assertFalse(actual);
    }
}