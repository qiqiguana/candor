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
    void testRemoveInterval_RemovedIntervalIsPartOfExistingInterval1() {
        Solution1272 solution = new Solution1272();
        int[][] intervals = {{1, 4}, {5, 8}};
        int[] toBeRemoved = {2, 6};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(6, 8));
        List<List<Integer>> actual = solution.removeInterval(intervals, toBeRemoved);
        assertEquals(expected, actual);
    }
}