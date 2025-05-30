package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1102.
*/
class Solution1102Test {
    @Test
    void testMaximumMinimumPath1() {
        Solution1102 solution = new Solution1102();
        int[][] grid = {{1, 3, 4}, {3, 5, 6}};
        assertEquals(1, solution.maximumMinimumPath(grid));
    }
}
