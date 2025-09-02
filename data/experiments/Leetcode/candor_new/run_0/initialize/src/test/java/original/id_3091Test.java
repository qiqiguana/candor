package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3091.
*/
class Solution3091Test {
    @Test
    void testMinOperationsShouldReturnCorrectResult() {
        Solution3091 solution = new Solution3091();
        int result = solution.minOperations(4);
        assertEquals(2, result);
    }
}