package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2862.
*/
class Solution2862Test {
    @Test
    void test_maximumSum() {
        Solution2862 solution = new Solution2862();
        List<Integer> nums = new java.util.ArrayList<>();
        nums.add(1);
        nums.add(4);
        nums.add(9);
        long expected = 9;
        assertEquals(expected, solution.maximumSum(nums));
    }
}
