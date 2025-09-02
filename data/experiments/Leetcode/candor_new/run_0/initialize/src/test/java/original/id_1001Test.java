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
}
