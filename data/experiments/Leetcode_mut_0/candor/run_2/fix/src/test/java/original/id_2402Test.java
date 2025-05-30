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
    void testMostBooked_OneMeeting_ReturnsZero() {
        // Arrange
        Solution2402 solution = new Solution2402();
        int n = 1;
        int[][] meetings = {{0, 30}};

        // Act
        int result = solution.mostBooked(n, meetings);

        // Assert
        assertEquals(0, result);
    }
}