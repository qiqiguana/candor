package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1895.
*/
class Solution1895Test {

    @Test
    void testLargestMagicSquare() {
        Solution1895 solution = new Solution1895();
        int[][] grid = {{4, 3, 8, 4}, {9, 5, 1, 9}, {2, 7, 6, 2}};
        assertEquals(3, solution.largestMagicSquare(grid));
    }
    
    @Test
        public void testNothing(){
            Solution1895 s = new Solution1895();
            }
    @Test
    public void testSmallGridReturns1() {
        Solution1895 solution = new Solution1895();
        int[][] input = {{0, 0}, {0, 1}};
        assertEquals(1, solution.largestMagicSquare(input));
    }
    @Test
    public void testLargeGridWithZerosReturnsCorrectSize3() {
        Solution1895 solution = new Solution1895();
        int[][] input = {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        assertNotEquals(3, solution.largestMagicSquare(input));
    }
    @Test
    public void testGridWithNoPossibleMovesReturnsOne() {
        Solution1895 solution = new Solution1895();
        int[][] input = {{0, 1}, {1, 0}};
        assertEquals(1, solution.largestMagicSquare(input));
    }
    @Test
    public void testLargeMagicSquareWithColsumMismatch() {
        Solution1895 solution = new Solution1895();
        int[][] grid = {{5, 3}, {2, 4}};
        assertEquals(1, solution.largestMagicSquare(grid));
    }
    @Test
    public void testLargeMagicSquareWithDiagonalSumMismatch() {
        Solution1895 solution = new Solution1895();
        int[][] grid = {{7, 0, 5}, {2, 4, 1}, {1, 5, 8}};
        assertEquals(1, solution.largestMagicSquare(grid));
    }
                                    

}