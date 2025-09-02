package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2152.
*/
class Solution2152Test {
    @Test
    void testMinimumLines() {
        Solution2152 solution = new Solution2152();
        int[][] points = {{3, 2}, {4, 10}, {1, 3}};
        assertEquals(2, solution.minimumLines(points));
    }
}