package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1901.
*/
class Solution1901Test {
    @Test
    void findPeakGrid_testCase1() {
        int[][] mat = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Solution1901 solution1901 = new Solution1901();
        assertArrayEquals(new int[] {2, 2}, solution1901.findPeakGrid(mat));
    }
}