package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2397.
*/
class Solution2397Test {
    @Test
    void test_maximumRows() {
        Solution2397 solution = new Solution2397();
        int[][] matrix = {{1,0},{1,0}};
        int numSelect = 1;
        int expected = 2;
        assertEquals(expected, solution.maximumRows(matrix, numSelect));
    }
}
