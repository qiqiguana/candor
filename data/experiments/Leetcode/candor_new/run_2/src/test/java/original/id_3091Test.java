package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3091.
*/
class Solution3091Test {
    @Test
    void testMinOperations_SimpleCase() {
        Solution3091 solution = new Solution3091();
        int k = 5;
        assertEquals(3, solution.minOperations(k));
    }
}