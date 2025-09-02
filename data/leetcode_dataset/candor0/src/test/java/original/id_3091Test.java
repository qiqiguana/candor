package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3091.
*/
class Solution3091Test {
    @Test
    void testMinOperations_KIs5_Returns3() {
        Solution3091 solution = new Solution3091();
        int k = 5;
        int expected = 3;
        assertEquals(expected, solution.minOperations(k));
    }
}