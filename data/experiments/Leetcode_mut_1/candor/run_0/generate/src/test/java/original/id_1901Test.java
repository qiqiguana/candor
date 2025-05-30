package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1901.
*/
class Solution1901Test {
    @Test
    void testFindPeakGrid_PeakAtLastRow() {
        Solution1901 solution = new Solution1901();
        int[][] mat = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertArrayEquals(new int[] {2, 2}, solution.findPeakGrid(mat));
    }
    
    @Test
        public void testNothing(){
            Solution1901 s = new Solution1901();
            }
    @Test
    public void testFindPeakGridWithPeakInLeftHalf1() {
        Solution1901 solution = new Solution1901();
        int[][] mat = {{5, 2}, {3, 4}};
        int[] expected = {0, 0};
        assertArrayEquals(expected, solution.findPeakGrid(mat));
    }
                                    
}