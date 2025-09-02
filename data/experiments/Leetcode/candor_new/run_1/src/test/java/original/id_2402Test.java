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
    void test_mostBooked_1() {
        // Arrange
        Solution2402 solution = new Solution2402();
        int n = 2;
        int[][] meetings = {{0,30},{5,10},{15,20}};
        int expected = 1;

        // Act
        int actual = solution.mostBooked(n, meetings);

        // Assert
        assertEquals(expected, actual);
    }
}
