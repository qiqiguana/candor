package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0035.
*/
class Solution0035Test {

    @Test
    void testSearchInsert_TargetExistsInArray() {
        Solution0035 solution = new Solution0035();
        int[] nums = {1, 3, 5, 6};
        int target = 5;
        int expectedOutput = 2;
        int actualOutput = solution.searchInsert(nums, target);
        assertEquals(expectedOutput, actualOutput);
    }
}