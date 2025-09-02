package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0517.
*/
class Solution0517Test {
    @Test
    void testFindMinMoves() {
        Solution0517 solution = new Solution0517();
        int[] machines = {1, 0, 5};
        assertEquals(3, solution.findMinMoves(machines));
    }
}