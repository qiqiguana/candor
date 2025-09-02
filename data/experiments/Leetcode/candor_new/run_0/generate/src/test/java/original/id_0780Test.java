package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0780.
*/
class Solution0780Test {
    @Test
    void testReachingPoints() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(2, 3, 7, 11);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints1() {
        Solution0780 s = new Solution0780();
        assertTrue(s.reachingPoints(1, 1, 3, 5));
    }
    @Test
    public void testSamePoint() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 1, 1, 1);
        assertTrue(result);
    }
    @Test
    public void testMultipleSxTxSameYCoordinate() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 2, 4, 2);
        assertFalse(result);
    }
    @Test
    public void testDifferentCoordinates2() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 2, 3, 5);
        assertTrue(result);
    }
    @Test
    public void testReachingPoints_with_tx_greater_than_sx_and_ty_greater_than_sy_but_not_reachable() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 1, 2, 2);
        assertFalse(result);
    }
}