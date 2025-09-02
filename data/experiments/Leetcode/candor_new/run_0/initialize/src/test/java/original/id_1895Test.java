package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1895.
*/
class Solution1895Test {
    @Test
    void testLargestMagicSquare() {
        // arrange
        int[][] grid = {{3,4,5},{6,7,2},{1,5,4}};
        Solution1895 solution1895 = new Solution1895();
        // act
        int result = solution1895.largestMagicSquare(grid);
        // assert
        assertEquals(1, result);
    }
}