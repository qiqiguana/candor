package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2869.
*/
class Solution2869Test {
    @Test
    void testMinOperations() {
        Solution2869 solution = new Solution2869();
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        int k = 3;
        assertEquals(3, solution.minOperations(nums, k));
    }
}