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
	void testShortestAlternatingPaths1() {
		Solution1129 solution = new Solution1129();
		int[][] redEdges = {{0, 1}, {1, 2}};
		int[][] blueEdges = {{0, 2}, {2, 1}};
		int[] expected = {0, 1, 1};
		assertArrayEquals(expected, solution.shortestAlternatingPaths(3, redEdges, blueEdges));
	}
 @Test
 public void ShortestAlternatingPathsTest_SelfLoops() {
     Solution1129 solution = new Solution1129();
     int n = 3;
     int[][] redEdges = {{0,0},{1,2}};
     int[][] blueEdges = {{2,1}};
     int[] expected_result = {0,-1,-1};
     assertArrayEquals(expected_result, solution.shortestAlternatingPaths(n, redEdges, blueEdges));
 }
}