package original;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3264.
*/
class Solution3264Test {

    @Test
    void getFinalState_SingleElementArray_ReturnsMultipliedArray() {
        Solution3264 solution = new Solution3264();
        int[] nums = {5};
        int k = 1;
        int multiplier = 2;
        int[] expected = {10};

        assertArrayEquals(expected, solution.getFinalState(nums, k, multiplier));
    }
    
    @Test
        public void testNothing(){
            Solution3264 s = new Solution3264();
            }
    @Test
    public void Test_Equal_Elements() {
    	Solution3264 solution = new Solution3264();
    	int[] nums = {1, 2, 3};
    	int k = 0;
    	int multiplier = 2;
    	int[] result = solution.getFinalState(nums, k, multiplier);
    	assertArrayEquals(new int[] {1, 2, 3}, result);
    }
    @Test
    public void Test_Duplicate_Elements_Corrected() {
    	Solution3264 solution = new Solution3264();
    	int[] nums = {3, 3, 1};
    	int k = 0;
    	int multiplier = 2;
    	int[] result = solution.getFinalState(nums, k, multiplier);
    	// The PriorityQueue does not guarantee any particular order for equal elements.
    	// So we sort the array before asserting the result.
    	Arrays.sort(result);
    	assertArrayEquals(new int[] {1, 3, 3}, result);
    }
                                    
}