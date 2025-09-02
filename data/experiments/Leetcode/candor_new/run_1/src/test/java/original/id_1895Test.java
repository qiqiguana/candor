package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1895.
*/
class Solution1895Test {

    @Test
    public void testLargestMagicSquare_1() {
        int[][] grid = {{7, 1, 4}, {3, 5, 8}};
        Solution1895 solution = new Solution1895();
        assertEquals(1, solution.largestMagicSquare(grid));
    }

}