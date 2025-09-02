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
        int[][] grid = {{3,4,5},{1,2,3}};
        assertEquals(3, solution.maximumMinimumPath(grid));
    }
}