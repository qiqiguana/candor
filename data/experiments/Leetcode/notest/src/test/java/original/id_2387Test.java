package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2387.
*/
class Solution2387Test {
    @Test
    void matrixMedian_SimpleGrid_ReturnsMiddleValue() {
        int[][] grid = new int[][] {{1, 3}, {2, 4}};
        Solution2387 solution2387 = new Solution2387();
        assertEquals(2, solution2387.matrixMedian(grid));
    }
}