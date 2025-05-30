package original;

import java.util.HashMap;

import java.util.Map;

import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2404.
*/
class Solution2404Test {
    @Test
    void testMostFrequentEven() {
        Solution2404 solution = new Solution2404();
        int[] nums = {1, 2, 2, 3, 4, 4, 5};
        assertEquals(2, solution.mostFrequentEven(nums));
    }
}