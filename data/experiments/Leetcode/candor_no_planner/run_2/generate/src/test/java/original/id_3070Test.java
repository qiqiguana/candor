package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3070.
*/
class Solution3070Test {
    @Test
    void testCountSubmatrices() {
        int[][] grid = {{1, 2}, {3, 4}};
        Solution3070 solution = new Solution3070();
        assertEquals(3, solution.countSubmatrices(grid, 5));
    }
}