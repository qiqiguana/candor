package original;

import java.util.PriorityQueue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3341.
*/
class Solution3341Test {
    @Test
    void testMinTimeToReach()
    {
        Solution3341 solution = new Solution3341();
        int[][] moveTime = {{3, 5, 4}, {2, 4, 3}, {2, 3, 4}};
        assertEquals(6, solution.minTimeToReach(moveTime));
    }
}