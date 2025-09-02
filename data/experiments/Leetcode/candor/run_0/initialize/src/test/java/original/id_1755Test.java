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
    void testMinAbsDifference2() {
        int[] nums = {1, -4, 0, 0, 3};
        int goal = -1;
        Solution1755 solution = new Solution1755();
        int expected = 0;
        int actual = solution.minAbsDifference(nums, goal);
        assertEquals(expected, actual);
    }
}