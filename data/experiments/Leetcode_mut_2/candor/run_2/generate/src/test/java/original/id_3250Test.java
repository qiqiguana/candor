package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3250.
*/
class Solution3250Test {
    @Test
    void testCountOfPairs() {
        Solution3250 solution = new Solution3250();
        int[] nums = {1, 2};
        assertEquals(3, solution.countOfPairs(nums));
    }
}
