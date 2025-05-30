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
        int[][] intervals = {{0, 5}};
        int[] toBeRemoved = {1, 3};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(3, 5));
        assertEquals(expected, solution.removeInterval(intervals, toBeRemoved));
    }
}