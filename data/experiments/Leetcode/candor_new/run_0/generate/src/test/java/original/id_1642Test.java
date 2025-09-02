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
    void testFurthestBuilding_WhenLaddersAreEnough_ReturnLastIndex() {
        // Arrange
        int[] heights = {4, 12, 5, 7, 3, 10, 9, 14, 10};
        int bricks = 100;
        int ladders = 1000;
        Solution1642 solution = new Solution1642();

        // Act
        int result = solution.furthestBuilding(heights, bricks, ladders);

        // Assert
        assertEquals(8, result);
    }
    @Test public void testFurthestBuilding_SimpleCase() { Solution1642 s = new Solution1642(); int[] heights = {4, 2, 7, 6, 9, 14, 12}; int bricks = 5; int ladders = 1; assertEquals(4, s.furthestBuilding(heights, bricks, ladders)); } @Test public void testFurthestBuilding_AnotherCase() { Solution1642 s = new Solution1642(); int[] heights = {4, 12, 2, 7, 3, 18, 20, 3, 19}; int bricks = 10; int ladders = 2; assertEquals(7, s.furthestBuilding(heights, bricks, ladders)); } @Test public void testFurthestBuilding_EdgeCase() { Solution1642 s = new Solution1642(); int[] heights = {14, 3, 19, 3}; int bricks = 17; int ladders = 0; assertEquals(3, s.furthestBuilding(heights, bricks, ladders)); }
}