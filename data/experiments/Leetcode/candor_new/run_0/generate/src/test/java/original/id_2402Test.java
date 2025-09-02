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
    @Test
    public void test_mostBooked_method() {
        Solution2402 s = new Solution2402();
        int n1 = 2;
        int[][] meetings1 = {{0,10},{1,5},{2,7},{3,4}};
        assertEquals(0, s.mostBooked(n1, meetings1));
    
        int n2 = 3;
        int[][] meetings2 = {{1,20},{2,10},{3,5},{4,9},{6,8}};
        assertEquals(1, s.mostBooked(n2, meetings2));
    }
}