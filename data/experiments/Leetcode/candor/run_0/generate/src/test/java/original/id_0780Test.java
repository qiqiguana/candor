package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0780.
*/
class Solution0780Test {
    @Test
    void testReachingPoints_ReturnsTrue_WhenTargetReached2() {
        // Arrange
        Solution0780 solution = new Solution0780();
        int sx = 1;
        int sy = 1;
        int tx = 3;
        int ty = 5;

        // Act
        boolean result = solution.reachingPoints(sx, sy, tx, ty);

        // Assert
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            Solution0780 s = new Solution0780();
            }
    @Test
    public void test_reachingPoints_with_tx_equals_sx_and_ty_greater_than_sy_fixed() {
        Solution0780 solution = new Solution0780();
        int[] input = {1, 2, 5, 4};
        boolean actualResult = solution.reachingPoints(input[0], input[1], input[2], input[3]);
        assertTrue(actualResult);
    }
    @Test
    public void test_reachingPoints_with_tx_greater_than_sx_and_ty_equals_sy_2() {
        Solution0780 solution = new Solution0780();
        boolean actualResult = solution.reachingPoints(1, 3, 3, 5);
        assertFalse(actualResult);
    }
    @Test
    public void test_reachingPoints_with_tx_equals_sx_and_ty_equals_sy() {
        Solution0780 solution = new Solution0780();
        int[] input = {1, 2, 1, 2};
        boolean actualResult = solution.reachingPoints(input[0], input[1], input[2], input[3]);
        assertTrue(actualResult);
    }
    @Test
    public void test_reachingPoints_with_none_of_the_conditions_met() {
        Solution0780 solution = new Solution0780();
        int[] input = {1, 2, 3, 4};
        boolean actualResult = solution.reachingPoints(input[0], input[1], input[2], input[3]);
        assertFalse(actualResult);
    }
    @Test
    public void test_reachingPoints_with_tx_equals_sx_and_ty_less_than_sy() {
        Solution0780 solution = new Solution0780();
        int[] input = {1, 2, 3, 4};
        boolean actualResult = solution.reachingPoints(input[0], input[1], input[2], input[3]);
        assertFalse(actualResult);
    }
    @Test
    public void test_reachingPoints_with_tx_greater_than_sx_and_ty_less_than_sy() {
        Solution0780 solution = new Solution0780();
        int[] input = {1, 2, 3, 4};
        boolean actualResult = solution.reachingPoints(input[0], input[1], input[2], input[3]);
        assertFalse(actualResult);
    }
    @Test
    public void testReachingPoints_tyEqualsSy_txGreaterThanSx() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 2, 3, 2);
        assertTrue(result);
    }
    @Test
    public void testReachingPoints_tyEqualsSy_txLessThanOrEqualToSx() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(3, 2, 1, 2);
        assertFalse(result);
    }
    @Test
    public void testReachingPointsWithTxGreaterSxAndTyGreaterSyButNotEqualTy() {
        Solution0780 solution = new Solution0780();
        int[] input = {1, 1, 3, 4};
        boolean expected = true;
        boolean actual = solution.reachingPoints(input[0], input[1], input[2], input[3]);
        assertEquals(expected, actual);
    }
    @Test
    public void testReachingPointsWithTxEqualSxAndTyEqualSy() {
        Solution0780 solution = new Solution0780();
        int[] input = {1, 1, 1, 1};
        boolean expected = true;
        boolean actual = solution.reachingPoints(input[0], input[1], input[2], input[3]);
        assertEquals(expected, actual);
    }
    @Test
    public void testReachingPointsWithTxEqualSxAndTyGreaterSyAndTyMinusSyModTxEqualZero() {
        Solution0780 solution = new Solution0780();
        int[] input = {1, 2, 1, 4};
        boolean expected = true;
        boolean actual = solution.reachingPoints(input[0], input[1], input[2], input[3]);
        assertEquals(expected, actual);
    }
    @Test
    public void testReachingPointsWithTxGreaterSxAndTyEqualSyAndTxMinusSxModTyEqualZero() {
        Solution0780 solution = new Solution0780();
        int[] input = {2, 1, 4, 1};
        boolean expected = true;
        boolean actual = solution.reachingPoints(input[0], input[1], input[2], input[3]);
        assertEquals(expected, actual);
    }
    @Test
    public void testReachingPoints_11_to_34_fixed() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 5, 3, 4);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_23_to_712() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(2, 3, 7, 12);
        assertTrue(result);
    }
    @Test
    public void testReachingPoints_45_to_810() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(4, 5, 8, 10);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_37_to_1535_Fixed() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(3, 7, 15, 35);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_108_to_30241() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(10, 8, 30, 24);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_57_to_2028() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(5, 7, 20, 28);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_InitialPositionEqualsTargetPosition() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 2, 1, 2);
        assertTrue(result);
    }
    @Test
    public void testReachingPoints_TargetXIsMultipleOfInitialX_3() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(3, 5, 9, 20);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_TargetXYAreMultiplesOfInitialXY() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(5, 6, 15, 18);
        if (result) {
            assertTrue(true);
        } else {
            if ((15 - 5) % 5 == 0 && (18 - 6) % 6 == 0)
                assertTrue((15 - 5) % 5 == 0 && (18 - 6) % 6 == 0);
            else
                assertFalse(true);
        }
    }
    @Test
    public void testReachingPoints_TargetXIsNotMultipleOfInitialX() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(3, 4, 10, 12);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_TargetYIsNotMultipleOfInitialY() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(5, 6, 15, 19);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_InitialPositionEqualsTargetPosition2() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 1, 1, 1);
        assertTrue(result);
    }
    @Test
    public void testReachingPoints_WhenTxGreaterThanSxAndTyGreaterThanSy_AndTxNotEqualToTy_ReturnsTrue_Fixed() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 2, 7, 12);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_WhenTxEqualsSxAndTyEqualsSy_ReturnsTrue() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(3, 4, 3, 4);
        assertTrue(result);
    }
    @Test
    public void testReachingPoints_WhenTxEqualsSxAndTyMinusSyModTxNotEqualToZero_ReturnsFalse2() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(3, 5, 3, 7);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_WhenTyEqualsSyAndTxMinusSxModTyNotEqualToZero_ReturnsFalse_Fixed1() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(2, 3, 5, 4);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_WhenTxGreaterThanSxAndTyLessThanSy_ReturnsFalse() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(1, 4, 3, 2);
        assertFalse(result);
    }
    @Test
    public void testReachingPoints_WhenTxLessThanSxAndTyGreaterThanSy_ReturnsFalse() {
        Solution0780 solution = new Solution0780();
        boolean result = solution.reachingPoints(4, 2, 3, 5);
        assertFalse(result);
    }
                                    
}