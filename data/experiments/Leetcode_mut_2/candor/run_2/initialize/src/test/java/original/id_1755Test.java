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
        int[] nums = {5, 10, 15};
        int goal = 20;
        int expected = 0;
        assertEquals(expected, solution.minAbsDifference(nums, goal));
    }
}