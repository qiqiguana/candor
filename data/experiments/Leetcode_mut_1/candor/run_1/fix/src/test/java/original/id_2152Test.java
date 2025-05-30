package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2152.
*/
class Solution2152Test {
    @Test
    void testMinimumLines_CollinearPoints_ReturnsOne() {
        int[][] points = {{1, 1}, {2, 2}, {3, 3}};
        Solution2152 solution = new Solution2152();
        assertEquals(1, solution.minimumLines(points));
    }
}