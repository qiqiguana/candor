package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1272.
*/
class Solution1272Test {
    @Test
    void testRemoveInterval() {
        Solution1272 solution = new Solution1272();
        int[][] intervals = {{0, 5}, {7, 10}};
        int[] toBeRemoved = {3, 8};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(0, 3), Arrays.asList(8, 10));
        assertEquals(expected, solution.removeInterval(intervals, toBeRemoved));
    }
    @Test
    public void testRemoveIntervalWithMultipleOverlaps() {
        Solution1272 solution = new Solution1272();
        int[][] intervals = {{-5, -4}, {-3, -2}, {1, 2}, {3, 5}, {8, 9}};
        int[] toBeRemoved = {-1, 4};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(-5, -4), Arrays.asList(-3, -2), Arrays.asList(4, 5), Arrays.asList(8, 9));
        assertEquals(expected, solution.removeInterval(intervals, toBeRemoved));
    }
}