package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2912.
*/
class Solution2912Test {
    @Test
    void testNumberOfWays() {
        Solution2912 solution = new Solution2912();
        int n = 3, m = 4, k = 1;
        int[] source = {0, 0};
        int[] dest = {0, 0};
        assertEquals(0, solution.numberOfWays(n, m, k, source, dest));
    }
}
