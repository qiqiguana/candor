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
    void testFurthestBuilding_WithBricksAndLadders_ReturnsCorrectIndex() {
        // Arrange
        int[] heights = {4, 12, 5, 7, 3, 15, 2, 8, 6};
        int bricks = 10;
        int ladders = 2;
        Solution1642 solution = new Solution1642();

        // Act and Assert
        assertEquals(8, solution.furthestBuilding(heights, bricks, ladders));
    }
}