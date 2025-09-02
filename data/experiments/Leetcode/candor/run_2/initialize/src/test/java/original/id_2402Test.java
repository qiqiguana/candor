package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2402.
*/
class Solution2402Test {
    @Test
    void testMostBooked() {
        Solution2402 solution = new Solution2402();
        int n = 3;
        int[][] meetings = {{1,50},{60,10},{140,60}};
        int expected = 0;
        assertEquals(expected, solution.mostBooked(n, meetings));
    }
}