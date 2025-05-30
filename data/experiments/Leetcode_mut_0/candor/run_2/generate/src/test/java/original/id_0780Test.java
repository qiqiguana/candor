package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0780.
*/
class Solution0780Test {

    @Test
    void testReachingPoints1() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(2, 3, 7, 11);
        assertFalse(result);
    }
}