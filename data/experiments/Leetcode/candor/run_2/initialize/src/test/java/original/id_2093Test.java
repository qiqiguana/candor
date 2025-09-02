package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Queue;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2093.
*/
class Solution2093Test {

    @Test
    void testMinimumCost() {
        // Arrange
        Solution2093 solution = new Solution2093();
        int n = 4;
        int[][] highways = {{0,1,4},{1,2,2},{0,2,5},{1,3,1}};
        int discounts = 1;
        // Act
        int actual = solution.minimumCost(n, highways, discounts);
        // Assert
        assertEquals(3, actual);
    }
}