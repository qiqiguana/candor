package original;

import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1642.
*/
class Solution1642Test {
    @Test
    void testFurthestBuilding() {
        Solution1642 solution = new Solution1642();
        int[] heights = {4, 12, 5, 7, 3, 10, 9, 8};
        int bricks = 100;
        int ladders = 1;
        assertEquals(7, solution.furthestBuilding(heights, bricks, ladders));
    }
}
