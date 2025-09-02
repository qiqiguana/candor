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
    void testMaxPoints() {
        //given
        int[][] grid = {{1, 2}, {2, 3}};
        int[] queries = {4};
        Solution2503 solution2503 = new Solution2503();

        //when
        int[] actual = solution2503.maxPoints(grid, queries);

        //then
        assertArrayEquals(new int[]{4}, actual);
    }
    @Test
    public void test_maxPoints_method() {
        Solution2503 s = new Solution2503();
        int[][] grid1 = {{1,2,3},{2,5,7},{3,5,1}};
        int[] queries1 = {5,6,2};
        assertArrayEquals(new int[]{5,8,1}, s.maxPoints(grid1, queries1));
    
        int[][] grid2 = {{5,2,1},{1,1,2}};
        int[] queries2 = {3};
        assertArrayEquals(new int[]{0}, s.maxPoints(grid2, queries2));
    }
}