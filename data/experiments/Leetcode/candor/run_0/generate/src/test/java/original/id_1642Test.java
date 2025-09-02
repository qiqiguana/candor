package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1642.
*/
class Solution1642Test {
    @Test
    void testFurthestBuilding_SingleStep() {
        Solution1642 solution = new Solution1642();
        int[] heights = {1, 2};
        int bricks = 0;
        int ladders = 1;
        assertEquals(1, solution.furthestBuilding(heights, bricks, ladders));
    }
}
