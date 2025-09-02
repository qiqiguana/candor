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
    void testMinAbsDifference() {
        Solution1755 solution = new Solution1755();
        int[] nums = {1, 2, -3, 6};
        int goal = 3;
        assertEquals(0, solution.minAbsDifference(nums, goal));
    }
    @Test
    public void test_minAbsDifference_Example1() {
        Solution1755 s = new Solution1755();
        int[] nums = {5,-7,3,5};
        int goal = 6;
        int expected = 0;
        assertEquals(expected, s.minAbsDifference(nums, goal));
    }
    @Test
    public void testMinAbsDifference_EmptyArray() {
        Solution1755 solution = new Solution1755();
        int[] nums = {};
        int goal = -5;
        int expected = 5;
        assertEquals(expected, solution.minAbsDifference(nums, goal));
    }
}