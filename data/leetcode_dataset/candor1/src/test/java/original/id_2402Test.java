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
    void testMostBooked(){
        // Arrange
        Solution2402 solution = new Solution2402();
        int n = 3;
        int[][] meetings = {{1,20},{2,30},{3,10}};
        int expectedResult = 0;

        // Act
        int actualResult = solution.mostBooked(n,meetings);
        
        // Assert
        assertEquals(expectedResult,actualResult);
    }
}