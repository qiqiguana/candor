package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2387.
*/
class Solution2387Test {
    @Test
    void testMatrixMedian() {
        int[][] grid = {{1,3,5},{2,6,4}};
        Solution2387 solution2387 = new Solution2387();
        assertEquals(3, solution2387.matrixMedian(grid));
    }
}