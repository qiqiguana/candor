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
    void testMostBooked_SimpleCase_ReturnsExpectedResult() {
        // Arrange
        int n = 3;
        int[][] meetings = {{1,50},{60,10},{100,10}};
        Solution2402 solution = new Solution2402();

        // Act
        int result = solution.mostBooked(n, meetings);

        // Assert
        assertEquals(0, result);
    }
}