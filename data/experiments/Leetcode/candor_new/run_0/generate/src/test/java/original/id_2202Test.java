package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2202.
*/
class Solution2202Test {
    @Test
    void test_maximumTop_kIsZero() {
        // Arrange
        Solution2202 solution = new Solution2202();
        int[] nums = {1, 2, 3};
        int k = 0;

        // Act
        int actual = solution.maximumTop(nums, k);

        // Assert
        assertEquals(1, actual);
    }
    @Test public void testMaximumTopEvenKEvenN() { Solution2202 s = new Solution2202(); int[] nums = {5, 2, 2, 4, 0, 6}; int k = 4; int expected = 5; int actual = s.maximumTop(nums, k); assertEquals(expected, actual); }
    @Test
    public void test_single_element_pile_odd_moves() {
        Solution2202 solution = new Solution2202();
        int[] nums = {2};
        int k = 1;
        int expected = -1;
        assertEquals(expected, solution.maximumTop(nums, k));
    }
    @Test
    public void test_single_element_pile_even_moves() {
        Solution2202 solution = new Solution2202();
        int[] nums = {2};
        int k = 2;
        int expected = 2;
        assertEquals(expected, solution.maximumTop(nums, k));
    }
    @Test
    public void testMaximumTop_KGreaterThanOrEqualN() {
        Solution2202 s = new Solution2202();
        int[] nums = {5, 2, 2, 4, 0, 6};
        int k = 10;
        assertEquals(6, s.maximumTop(nums, k));
    }
}