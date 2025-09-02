package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1755.
*/
class Solution1755Test {
    @Test
    void testMinAbsDifference1() {
        Solution1755 solution = new Solution1755();
        int[] nums = {1, 2, -3, 8, -4};
        int goal = 6;
        int expected = 0;
        assertEquals(expected, solution.minAbsDifference(nums, goal));
    }
}