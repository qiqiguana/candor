package original;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1001.
*/
class Solution1001Test {

@Test
void testGridIlluminationWithNoLamps() {
    Solution1001 solution = new Solution1001();
    int n = 5;
    int[][] lamps = {};
    int[][] queries = {{0, 0}, {4, 4}};
    int[] expected = {0, 0};
    assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
}

}