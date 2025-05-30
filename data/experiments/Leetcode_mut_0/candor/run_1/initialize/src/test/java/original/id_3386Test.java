package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3386.
*/
class Solution3386Test {
    @Test
    void testButtonWithLongestTime() {
        Solution3386 solution = new Solution3386();
        int[][] events = {{1, 3}, {2, 4}, {3, 5}};
        assertEquals(1, solution.buttonWithLongestTime(events));
    }
}
