package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2397.
*/
class Solution2397Test {
    @Test
    void testMaximumRows_0() {
        Solution2397 solution = new Solution2397();
        int[][] matrix = {{1,0},{1,0}};
        assertEquals(2, solution.maximumRows(matrix, 1));
    }
}