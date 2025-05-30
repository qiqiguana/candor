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
    
    @Test
        public void testNothing(){
            Solution3417 s = new Solution3417();
            }
    @Test
    public void zigzagTraversal_empty_grid() {
        Solution3417 solution = new Solution3417();
        int[][] grid = {};
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, solution.zigzagTraversal(grid));
    }
    @Test
    public void zigzagTraversal_null_grid() {
        Solution3417 solution = new Solution3417();
        int[][] grid = null;
        assertThrows(NullPointerException.class, () -> solution.zigzagTraversal(grid));
    }
    @Test
    public void testZigzagTraversalOddRowCountVarLen3() {
        Solution3417 solution = new Solution3417();
        int[][] grid = {{1, 2}, {3, 4, 5}};
        List<Integer> expected = new ArrayList<>();
        boolean ok = true;
        for (int i = 0; i < grid.length; ++i) {
            if (i % 2 == 1) {
                for (int j = grid[i].length - 1; j >= 0; --j) {
                    if (ok) {
                        expected.add(grid[i][j]);
                    }
                    ok = !ok;
                }
            } else {
                for (int x : grid[i]) {
                    if (ok) {
                        expected.add(x);
                    }
                    ok = !ok;
                }
            }
        }
        assertEquals(expected, solution.zigzagTraversal(grid));
    }
                                    
}