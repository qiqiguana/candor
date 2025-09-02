package original;

import java.util.PriorityQueue;

import java.util.Arrays;

import java.util.Queue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3341.
*/
class Solution3341Test {
    @Test
    void testMinTimeToReach() {
        Solution3341 solution = new Solution3341();
        int[][] moveTime = {{3, 2}, {4, 1}};
        int expected = 4;
        assertEquals(expected, solution.minTimeToReach(moveTime));
    }
}