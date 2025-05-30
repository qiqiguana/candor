package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1955.
*/
class Solution1955Test {
    @Test
    void testCountSpecialSubsequences() {
        Solution1955 solution = new Solution1955();
        int[] nums = {0, 1, 2};
        int expected = 1;
        assertEquals(expected, solution.countSpecialSubsequences(nums));
    }
}
