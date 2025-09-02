package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2912.
*/
class Solution2912Test {
    @Test
    void testNumberOfWays_SourceEqualsDest_ReturnsF0() {
        // Arrange
        Solution2912 solution = new Solution2912();
        int n = 10;
        int m = 10;
        int k = 1;
        int[] source = {0, 0};
        int[] dest = {0, 0};

        // Act
        int result = solution.numberOfWays(n, m, k, source, dest);

        // Assert
        assertEquals(0, result);
    }
    @Test
    public void testNumberOfWays_1(){
        Solution2912 s = new Solution2912();
        int result = s.numberOfWays(3, 2, 2, new int[]{1, 1}, new int[]{2, 2});
        assertEquals(2, result);
    }
    @Test
    public void TestDestAtSameColumnAsSourceButDifferentRow2() {
        assertNotEquals(0, new Solution2912().numberOfWays(4, 2, 100000, new int[] {1, 2}, new int[] {4, 2}));
    }
    @Test
    public void testNumberOfWays_AdjacentCells() {
        Solution2912 s = new Solution2912();
        int n = 3;
        int m = 2;
        int k = 1; // Change the value of k to 1, since it's adjacent cells
        int[] source = {1, 1};
        int[] dest = {1, 2};
        assertEquals(1, s.numberOfWays(n, m, k, source, dest));
    }
}