package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3287.
*/
class Solution3287Test {
    @Test
    void testMaxValue_SimpleCase() {
        Solution3287 solution = new Solution3287();
        int[] nums = {1, 2};
        int k = 1;
        int expectedResult = 3;
        int actualResult = solution.maxValue(nums, k);
        assertEquals(expectedResult, actualResult);
    }
}