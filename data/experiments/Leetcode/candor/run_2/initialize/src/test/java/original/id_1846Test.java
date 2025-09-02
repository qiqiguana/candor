package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1846.
*/
class Solution1846Test {
    @Test
    void testMaximumElementAfterDecrementingAndRearranging_SimpleCase() {
        Solution1846 solution = new Solution1846();
        int[] arr = {2, 2, 1, 2, 1};
        int expected = 2;
        assertEquals(expected, solution.maximumElementAfterDecrementingAndRearranging(arr));
    }
}