package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3070.
*/
class Solution3070Test {
    @Test
    void testCountSubmatrices() {
        Solution3070 solution = new Solution3070();
        int[][] grid = {{1, 1, 3}, {2, 2, 3}};
        int k = 4;
        assertEquals(3, solution.countSubmatrices(grid, k));
    }
}
