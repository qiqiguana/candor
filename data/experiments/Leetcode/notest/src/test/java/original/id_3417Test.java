package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3417.
*/
class Solution3417Test {

    @Test
    void testZigzagTraversal_EmptyGrid() {
        Solution3417 solution = new Solution3417();
        int[][] grid = {};
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, solution.zigzagTraversal(grid));
    }
}