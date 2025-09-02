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
void maximumSum1() {
        Solution2862 solution = new Solution2862();
        List<Integer> nums = new ArrayList<>();
nums.add(100);
        long actual = solution.maximumSum(nums);
long expected = 100;
assertEquals(expected,actual);
}
}