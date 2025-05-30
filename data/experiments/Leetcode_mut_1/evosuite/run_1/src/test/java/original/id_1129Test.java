package original;

import java.util.ArrayDeque;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.Deque;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1129.
*/
class Solution1129Test {
    @Test
    void testShortestAlternatingPaths() {
        Solution1129 solution = new Solution1129();
        int n = 3;
        int[][] redEdges = {{0,1},{1,2}};
        int[][] blueEdges = {};
        int[] expected = {0,1,-1};
        assertArrayEquals(expected, solution.shortestAlternatingPaths(n, redEdges, blueEdges));
    }
}