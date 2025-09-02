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
    void testRemoveInterval_NoOverlap() {
        Solution1272 solution = new Solution1272();
        int[][] intervals = {{1, 3}, {5, 7}};
        int[] toBeRemoved = {4, 6};
        List<List<Integer>> expected = Arrays.asList(
            Arrays.asList(1, 3), 
            Arrays.asList(6, 7)
        );
        assertEquals(expected, solution.removeInterval(intervals, toBeRemoved));
    }
}