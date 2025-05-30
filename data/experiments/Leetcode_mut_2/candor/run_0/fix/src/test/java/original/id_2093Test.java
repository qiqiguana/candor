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
        Solution2093 solution = new Solution2093();
        int n = 5;
        int[][] highways = {{0,1,4},{1,2,2},{2,3,6},{3,1,1}};
        int discounts = 1;
        assertEquals(-1, solution.minimumCost(n, highways, discounts));
    }
}