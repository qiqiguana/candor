package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3171.
*/
class Solution3171Test {
    @Test
    void testMinimumDifference_Simple_2() {
        int[] nums = {1, 5, 7};
        int k = 10;
        Solution3171 solution = new Solution3171();
        assertEquals(3, solution.minimumDifference(nums, k));
    }
    
    @Test
        public void testNothing(){
            Solution3171 s = new Solution3171();
            }
    @Test
    public void TestMinimumDifferenceWithMultipleBitsSet() {
        Solution3171 solution = new Solution3171();
        int[] nums = {7, 15, 23};
        int k = 10;
        int expected = 3;
        int actual = solution.minimumDifference(nums, k);
        assertEquals(expected, actual);
    }
                                    
}