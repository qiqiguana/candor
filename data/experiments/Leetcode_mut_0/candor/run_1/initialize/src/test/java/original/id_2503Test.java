package original;

import java.util.PriorityQueue;

import java.util.Arrays;

import java.util.Queue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2503.
*/
class Solution2503Test {
  @Test
  void test_maxPoints() {
    int[][] grid = {{1,2},{2,3}};
    int[] queries = {4};
    int[] expected = {4};
    Solution2503 solution = new Solution2503();
    assertArrayEquals(expected, solution.maxPoints(grid, queries));
  }
}
