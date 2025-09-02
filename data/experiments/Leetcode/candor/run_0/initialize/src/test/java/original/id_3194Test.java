package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3194.
*/
class Solution3194Test {
    @Test
    void test_minimumAverage() {
        Solution3194 solution = new Solution3194();
        int[] nums = {1, 2, 3, 4, 5};
        double expected = 3;
        assertEquals(expected, solution.minimumAverage(nums));
    }
}