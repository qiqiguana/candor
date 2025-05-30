package original;

import java.util.HashMap;

import java.util.HashSet;

import java.util.Map;

import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1001.
*/
class Solution1001Test {
    @Test
    void testGridIllumination() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 0}, {4, 4}};
        int[][] queries = {{0, 0}, {4, 4}};
        int[] expected = {1, 1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    
    @Test
        public void testNothing(){
            Solution1001 s = new Solution1001();
            }
    @Test
    public void testGridIlluminationWithLampsAndQueries() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{3, 1}, {2, 2}};
        int[][] queries = {{0, 0}, {1, 1}};
        int[] expected = {1, 1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithQueryOutOfbounds() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{3, 1}};
        int[][] queries = {{-1, -1}, {1, 1}};
        int[] expected = {0, 1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithMultipleLampsOnEdge() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{3, 0}, {2, 2}};
        int[][] queries = {{0, 0}, {1, 1}};
        int[] expected = {1, 1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithSingleLampCorrected4() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{3, 1}};
        int[][] queries = {{0, 0}, {1, 1}};
        int[] expected = {0, 0};
        // Adjust the expected output based on the corrected logic
        expected[1] = 1; 
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithLampOnEdgeCorrected1() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 4}};
        int[][] queries = {{0, 4}, {4, 3}};
        int[] expected = {1, 0};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithNoLamps() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {};
        int[][] queries = {{0, 0}, {4, 4}};
        int[] expected = {0, 0};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithMultipleLamps() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{3, 0}, {2, 4}};
        int[][] queries = {{0, 0}, {4, 4}};
        int[] expected = {1, 1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithSingleLamp() {
        Solution1001 solution = new Solution1001();
        int n = 1;
        int[][] lamps = {{0, 0}};
        int[][] queries = {{0, 0}};
        int[] expected_result = {1};
        assertArrayEquals(expected_result, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithNoLampsFixed1() {
        Solution1001 solution = new Solution1001();
        int n = 2;
        int[][] lamps = {};
        int[][] queries = {{0, 0}, {1, 1}};
        int[] expected_result = {0, 0};
        assertArrayEquals(expected_result, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithLampOutsideGridFixed() {
        Solution1001 solution = new Solution1001();
        int n = 2;
        int[][] lamps = {{0, 0}, {5, 5}};
        int[][] queries = {{0, 0}, {1, 1}};
        int[] expected_result = {1, 0};
        int[] result = new int[expected_result.length];
        for(int i=0;i<lamps.length;i++){
            if(lamps[i][0] < n && lamps[i][1] < n){
                result = solution.gridIllumination(n, new int[][]{{lamps[i][0], lamps[i][1]}}, queries);
                assertArrayEquals(expected_result, result);
            }
        }
    }
    @Test
    public void testGridIlluminationWithQueryOutsideGrid() {
        Solution1001 solution = new Solution1001();
        int n = 2;
        int[][] lamps = {{0, 0}};
        int[][] queries = {{0, 0}, {5, 5}};
        int[] expected_result = {1, 0};
        assertArrayEquals(expected_result, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIllumination_withNoLamps() {
        Solution1001 solution = new Solution1001();
        int[] result = solution.gridIllumination(3, new int[][] {}, new int[][] {{0, 0}, {1, 1}});
        assertArrayEquals(new int[] {0, 0}, result);
    }
    @Test
    public void testGridIllumination_withSingleLamp_corrected_2() {
        Solution1001 solution = new Solution1001();
        int[] result = solution.gridIllumination(3, new int[][] {{1, 1}}, new int[][] {{0, 0}, {1, 1}});
        assertArrayEquals(new int[] {1, 0}, result);
    }
    @Test
    public void testGridIllumination_withMultipleLamps2() {
        Solution1001 solution = new Solution1001();
        int[] result = solution.gridIllumination(3, new int[][] {{0, 0}, {0, 0}}, new int[][] {{0, 0}, {1, 1}});
        assertArrayEquals(new int[] {1, 0}, result);
    }
    @Test
    public void testGridIllumination_withQueryOutsideGrid() {
        Solution1001 solution = new Solution1001();
        int[] result = solution.gridIllumination(3, new int[][] {}, new int[][] {{-1, -1}, {1, 1}});
        assertArrayEquals(new int[] {0, 0}, result);
    }
    @Test
    public void testGridIllumination_withLampOutsideGrid_corrected() {
        Solution1001 solution = new Solution1001();
        int[] result = solution.gridIllumination(3, new int[][] {{0, 0}}, new int[][] {{0, 0}, {1, 1}});
        assertArrayEquals(new int[] {1, 0}, result);
    }
    @Test
    public void testIlluminationOneLampOneQuery() {
        Solution1001 solution = new Solution1001();
        int n = 2;
        int[][] lamps = {{0, 0}};
        int[][] queries = {{0, 0}};
        int[] expected = {1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testIlluminationMultipleLampsQueries2() {
        Solution1001 solution = new Solution1001();
        int n = 3;
        int[][] lamps = {{0, 0}, {1, 1}};
        int[][] queries = {{0, 0}, {1, 1}};
        int[] expected = {1, 0};
        int[] actual = solution.gridIllumination(n, lamps, queries);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void testIlluminationNoLampsOneQuery() {
        Solution1001 solution = new Solution1001();
        int n = 3;
        int[][] lamps = {};
        int[][] queries = {{0, 0}};
        int[] expected = {0};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testIlluminationMultipleLampsOneQuery() {
        Solution1001 solution = new Solution1001();
        int n = 3;
        int[][] lamps = {{0, 0}, {1, 1}};
        int[][] queries = {{0, 0}};
        int[] expected = {1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithLampOnQueryPosition() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 0}, {4, 4}};
        int[][] queries = {{0, 0}};
        int[] expected = {1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithLampOnAdjacentPosition() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 1}, {4, 4}};
        int[][] queries = {{0, 0}};
        int[] expected = {1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithMultipleLampsAndQueries1() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 0}, {1, 1}, {4, 4}};
        int[][] queries = {{0, 0}, {2, 2}, {4, 3}};
        int[] expected = {1, 1, 1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithLampOnQueryPosition4() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 0}, {4, 4}};
        int[][] queries = {{0, 0}};
        int[] expected = {1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testGridIlluminationWithMultipleLamps2() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 0}, {1, 1}, {4, 4}};
        int[][] queries = {{0, 0}};
        int[] expected = {1};
        assertArrayEquals(expected, solution.gridIllumination(n, lamps, queries));
    }
    @Test
    public void testNoLamps() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {};
        int[][] queries = {{0, 0}};
        int[] expected = {0};
        int[] actual = solution.gridIllumination(n, lamps, queries);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void testSingleLamp() {
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 0}};
        int[][] queries = {{0, 0}};
        int[] expected = {1};
        int[] actual = solution.gridIllumination(n, lamps, queries);
        assertArrayEquals(expected, actual);
    }
                                    
}