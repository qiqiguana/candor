package original;

import java.util.ArrayDeque;

import java.util.Deque;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0739.
*/
class Solution0739Test {
    @Test
    void testDailyTemperatures() {
        Solution0739 solution = new Solution0739();
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] expected = {1, 1, 4, 2, 1, 1, 0, 0};
        assertArrayEquals(expected, solution.dailyTemperatures(temperatures));
    }
}