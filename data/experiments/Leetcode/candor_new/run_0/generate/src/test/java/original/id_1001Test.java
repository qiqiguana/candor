package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1001.
*/
class Solution1001Test {
    @Test
    void testGridIllumination() {
        int n = 5;
        int[][] lamps = {{0,0},{0,4},{4,0},{4,4}};
        int[][] queries = {{0,4},{3,3},{4,4}};
        int[] expected = {1,1,1};
        assertArrayEquals(expected, new Solution1001().gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationSingleLampQuery() {
        Solution1001 s = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 0}};
        int[][] queries = {{0, 0}};
        int[] expected = {1};
        assertArrayEquals(expected, s.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIllumination_diagonalLamp_adjacentQueries_2() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 0}, {4, 4}};
        int[][] queries = {{1, 1}, {2, 3}};
        int[] expected = {1, 0};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void gridIllumination_4() {
        int n = 5;
        int[][] lamps = {{0, 0}, {4, 3}};
        int[][] queries = {{1, 2}, {1, 3}};
        Solution1001 solution1001 = new Solution1001();
        int[] result = solution1001.gridIllumination(n, lamps, queries);
        assertArrayEquals(new int[]{0, 1}, result);
    }
}