package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1901.
*/
class Solution1901Test {
    @Test
    void findPeakGrid_SimpleCase() {
        int[][] mat = {{10, 20}, {30, 2}};
        Solution1901 solution = new Solution1901();
        assertArrayEquals(new int[] {0, 1}, solution.findPeakGrid(mat));
    }
}