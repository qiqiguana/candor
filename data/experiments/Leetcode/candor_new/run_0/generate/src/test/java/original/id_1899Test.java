package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1899.
*/
class Solution1899Test {
    @Test
    void testMergeTriplets() {
        Solution1899 solution = new Solution1899();
        int[][] triplets = {{2,5,3},{1,8,4},{1,7,5}};
        int[] target = {2, 5, 5};
        assertFalse(solution.mergeTriplets(triplets, target));
    }
    @Test
    public void testMergeTripletsValidInput1() {
        Solution1899 s = new Solution1899();
        int[][] triplets = {{2,5,3},{1,8,4},{1,7,5}};
        int[] target = {2,7,5};
        assertTrue(s.mergeTriplets(triplets, target));
    }
    @Test public void testSingleTripletDoesNotMatchTarget2() { int[][] triplets = new int[][] {{2, 5, 3}}; int[] target = new int[] {2, 7, 3}; assertFalse(new Solution1899().mergeTriplets(triplets, target)); }
    @Test public void testMultipleTripletsMatchTarget2() { int[][] triplets = new int[][] {{2, 5, 3}, {1, 8, 4}}; int[] target = new int[] {2, 8, 3}; assertFalse(new Solution1899().mergeTriplets(triplets, target)); }
    @Test public void testMultipleTripletsDoNotMatchTarget2() { int[][] triplets = new int[][] {{2, 5, 3}, {1, 8, 4}}; int[] target = new int[] {3, 7, 4}; assertFalse(new Solution1899().mergeTriplets(triplets, target)); }
}