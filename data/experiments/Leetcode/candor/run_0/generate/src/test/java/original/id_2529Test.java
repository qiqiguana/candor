package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2529.
*/
class Solution2529Test {

    @Test
    void testMaximumCount() {
        Solution2529 solution = new Solution2529();
        int[] nums = {1, -3, 2, 3, -4};
        assertEquals(3, solution.maximumCount(nums));
    }
    
    @Test
        public void testNothing(){
            Solution2529 s = new Solution2529();
            }
    @Test
    public void test_maximumCount_with_positive_numbers_1() {
        Solution2529 solution = new Solution2529();
        int[] nums = {1, 2, 3, 4};
        assertEquals(4, solution.maximumCount(nums));
    }
    @Test
    public void test_maximumCount_with_negative_numbers_2() {
        Solution2529 solution = new Solution2529();
        int[] nums = {-1, -2, -3, -4};
        assertEquals(4, solution.maximumCount(nums));
    }
    @Test
    public void test_maximumCount_with_mixed_numbers_3() {
        Solution2529 solution = new Solution2529();
        int[] nums = {1, -2, 3, -4, 5, -6};
        assertEquals(3, solution.maximumCount(nums));
    }
    @Test
    public void test_maximumCount_with_zeros_4() {
        Solution2529 solution = new Solution2529();
        int[] nums = {0, 0, 0, 0};
        assertEquals(0, solution.maximumCount(nums));
    }
                                    
}