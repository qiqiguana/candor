package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3341.
*/
class Solution3341Test {

    @Test
    void testMinTimeToReach()
    {
        // Arrange
        Solution3341 solution = new Solution3341();
        int[][] moveTime = {{2,3},{5,4}};

        // Act
        int actual = solution.minTimeToReach(moveTime);
        int expected = 5;

        // Assert
        assertEquals(expected,actual);
    }
}