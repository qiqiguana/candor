package original;

import java.util.ArrayDeque;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.Deque;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2392.
*/
class Solution2392Test {
    @Test
    void testBuildMatrix() {
        Solution2392 solution = new Solution2392();
        int k = 3;
        int[][] rowConditions = {{1, 2}, {3, 2}};
        int[][] colConditions = {{2, 1}, {3, 2}};
        int[][] expected = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        int[][] actual = solution.buildMatrix(k, rowConditions, colConditions);
        assertEquals(expected.length, actual.length);
    }
}