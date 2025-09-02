package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testMinimumCost_WhenDiscountsIsZero_ReturnsCostOfCheapestPath() {
        // Arrange
        int n = 4;
        int[][] highways = {{0,1,8},{0,2,4},{1,2,5},{1,3,7}};
        Solution2093 solution = new Solution2093();
        
        // Act
        int minimumCost = solution.minimumCost(n, highways, 0);
        
        // Assert
        assertEquals(15, minimumCost);
    }
    @Test
    public void testUnreachableCity() {
        Solution2093 solution = new Solution2093();
        int result = solution.minimumCost(4, new int[][] {{0,1,3},{2,3,2}}, 0);
        assertEquals(-1, result);
    }
}