package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3341.
*/
class Solution3341Test {
    @Test
    void testMinTimeToReach() {
        int[][] moveTime = {{1, 2}, {3, 4}};
        Solution3341 solution = new Solution3341();
        assertEquals(5, solution.minTimeToReach(moveTime));
    }
}