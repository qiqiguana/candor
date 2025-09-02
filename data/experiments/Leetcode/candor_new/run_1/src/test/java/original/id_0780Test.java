package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0780.
*/
class Solution0780Test {
    @Test
    void testReachingPoints_WhenSXEqualsTXAndSYEqualsTY_ReturnsTrue1() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 1, 1, 1);
        assertTrue(result);
    }
}