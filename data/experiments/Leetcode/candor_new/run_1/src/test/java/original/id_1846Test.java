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
        int[] arr = {2, 3, 1, 4};
        int expected = 4;
        assertEquals(expected, solution.maximumElementAfterDecrementingAndRearranging(arr));
    }
}