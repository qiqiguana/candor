package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0593.
*/
class Solution0593Test {
    @Test
    void testValidSquare1() {
        Solution0593 solution = new Solution0593();
        int[] p1 = {1, 1};
        int[] p2 = {-1, -1};
        int[] p3 = {-1, 1};
        int[] p4 = {1, -1};
        assertTrue(solution.validSquare(p1, p2, p3, p4));
    }
}