package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0593.
*/
class Solution0593Test {
    @Test
    void testValidSquare() {
        Solution0593 solution = new Solution0593();
        int[] p1 = {0, 1};
        int[] p2 = {1, 2};
        int[] p3 = {2, 1};
        int[] p4 = {1, 0};
        assertTrue(solution.validSquare(p1, p2, p3, p4));
    }
    @Test
    public void testNonSquareWithSameLengths1() {
        Solution0593 solution = new Solution0593();
        int[] p1 = {0, 0};
        int[] p2 = {1, 2};
        int[] p3 = {3, 4};
        int[] p4 = {5, 6};
        assertFalse(solution.validSquare(p1, p2, p3, p4));
    }
    @Test
    public void testValidSquareTwoIdenticalPoints1Fixed() {
        Solution0593 s = new Solution0593();
        int[] p1 = {0, 0};
        int[] p2 = {0, 0};
        int[] p3 = {1, 0};
        int[] p4 = {0, 1};
        assertFalse(s.validSquare(p1, p2, p3, p4));
    }
    @Test
    public void testInvalidSquareWithUnequalSides() {
        Solution0593 solution = new Solution0593();
        int[] p1 = {0, 0};
        int[] p2 = {1, 1};
        int[] p3 = {1, 0};
        int[] p4 = {0, 12};
        boolean result = solution.validSquare(p1, p2, p3, p4);
        assertFalse(result);
    }
    @Test
    public void testValidSquareWithDifferentOrientation2() {
        Solution0593 solution = new Solution0593();
        int[] p1 = {0, 0};
        int[] p2 = {1, 0};
        int[] p3 = {0, 1};
        int[] p4 = {-1, 0};
        boolean result = solution.validSquare(p1, p2, p3, p4);
        assertFalse(result);
    }
    @Test
    public void testInvalidSquareWithCollinearPoints() {
        Solution0593 solution = new Solution0593();
        int[] p1 = {0, 0};
        int[] p2 = {1, 0};
        int[] p3 = {2, 0};
        int[] p4 = {3, 0};
        boolean result = solution.validSquare(p1, p2, p3, p4);
        assertFalse(result);
    }
    @Test
    public void TestValidSquare_DegenerateCase() {
        Solution0593 solution = new Solution0593();
        int[][] input = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
        assertFalse(solution.validSquare(input[0], input[1], input[2], input[3]));
    }
}