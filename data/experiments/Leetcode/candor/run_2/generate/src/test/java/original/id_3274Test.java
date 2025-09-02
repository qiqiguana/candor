package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3274.
*/
class Solution3274Test {
    @Test
    void testCheckTwoChessboards() {
        Solution3274 solution = new Solution3274();
        boolean result = solution.checkTwoChessboards("A1", "B2");
        assertTrue(result);
    }
}