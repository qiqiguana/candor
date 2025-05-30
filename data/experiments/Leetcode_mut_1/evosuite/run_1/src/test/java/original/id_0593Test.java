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
        int[] p1 = {0, 0};
        int[] p2 = {0, 4};
        int[] p3 = {4, 0};
        int[] p4 = {4, 4};
        assertTrue(solution.validSquare(p1, p2, p3, p4));
    }
}