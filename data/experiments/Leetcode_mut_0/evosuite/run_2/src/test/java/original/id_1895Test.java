package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1895.
*/
class Solution1895Test {

    @Test
    void testLargestMagicSquare() {
        Solution1895 solution = new Solution1895();
        int[][] grid = {{4, 3, 8, 4}, {9, 5, 1, 9}, {2, 7, 6, 2}};
        assertEquals(3, solution.largestMagicSquare(grid));
    }

}