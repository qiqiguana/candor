package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2328.
*/
class Solution2328Test {
    @Test
    void testCountPaths()
    {
        Solution2328 solution = new Solution2328();
        int[][] grid = {{1, 1}, {3, 4}};
        assertEquals(8, solution.countPaths(grid));
    }
}
