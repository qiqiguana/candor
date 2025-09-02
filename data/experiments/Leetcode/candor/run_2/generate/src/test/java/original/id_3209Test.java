package original;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution3209.
*/
class Solution3209Test {
    @Test
    void testCountSubarrays() {
        Solution3209 solution = new Solution3209();
        int[] nums = {1, 2, 3};
        int k = 2;
        long expected = 2;
        long actual = solution.countSubarrays(nums, k);
        assertEquals(expected, actual);
    }
}
