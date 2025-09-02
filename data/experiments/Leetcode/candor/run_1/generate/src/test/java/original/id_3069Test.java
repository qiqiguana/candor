package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3069.
*/
class Solution3069Test {
    @Test
    void testResultArray() {
        Solution3069 solution = new Solution3069();
        int[] nums = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, solution.resultArray(nums));
    }
}