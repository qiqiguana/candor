package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.PriorityQueue;
import java.util.Queue;

/**
* Test class of Solution1642.
*/
class Solution1642Test {
    @Test
    void testFurthestBuilding_WhenCalled_ReturnsCorrectResult() {
        // Arrange
        int[] heights = {4, 12, 5, 7, 3, 15, 3, 8};
        int bricks = 10;
        int ladders = 2;
        Solution1642 solution = new Solution1642();

        // Act
        int result = solution.furthestBuilding(heights, bricks, ladders);

        // Assert
        assertEquals(7, result);
    }
}