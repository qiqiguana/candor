package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1901.
*/
class Solution1901Test {

@Test
void testFindPeakGrid() {
    Solution1901 solution = new Solution1901();
    int[][] mat = {{1,2,3},{4,5,6},{7,8,9}};
    assertArrayEquals(new int[] {2, 2}, solution.findPeakGrid(mat));
}
}