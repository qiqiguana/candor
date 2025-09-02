package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3235.
*/
class Solution3235Test {
    @Test
    void testCanReachCorner() {
        var solution = new Solution3235();
        int[][] circles = {{1, 1, 3}, {2, 2, 4}};
        assertFalse(solution.canReachCorner(0, 0, circles));
    }
}
