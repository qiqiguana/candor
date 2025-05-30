package original;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2605.
*/
class Solution2605Test {
    @Test
    void testMinNumber() {
        Solution2605 solution = new Solution2605();
        int[] nums1 = {4,1,3};
        int[] nums2 = {5,7,9};
        assertEquals(15, solution.minNumber(nums1, nums2));
    }
    
    @Test
        public void testNothing(){
            Solution2605 s = new Solution2605();
            }
    @Test void testMinNumberWithSameElement() { Solution2605 solution = new Solution2605(); int[] nums1 = { 1, 2 }; int[] nums2 = { 2, 3 }; assertEquals(2, solution.minNumber(nums1, nums2)); }
                                    
}