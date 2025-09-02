package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Solution2862.
*/
class Solution2862Test {
    @Test
    void testMaximumSum() {
        Solution2862 solution = new Solution2862();
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(4);
        nums.add(9);
        assertEquals(9, solution.maximumSum(nums));
    }
}