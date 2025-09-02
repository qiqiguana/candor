package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2577.
*/
class Solution2577Test {
    @Test
    void testMinimumTime() {
        int[][] grid = {{0,1,2},{3,4,5}};
        Solution2577 solution2577 = new Solution2577();
        assertEquals( 5 ,solution2577.minimumTime(grid));
    }
}