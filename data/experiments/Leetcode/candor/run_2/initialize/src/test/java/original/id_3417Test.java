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
    void testZigzagTraversal() {
        Solution3417 solution = new Solution3417();
        int[][] grid = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        List<Integer> expected = new ArrayList<>(List.of(1, 3, 5, 7, 9));
        assertEquals(expected, solution.zigzagTraversal(grid));
    }
}