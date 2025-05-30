package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3398.
*/
class Solution3398Test {
    @Test
    void testMinLength() {
        Solution3398 solution = new Solution3398();
        String s = "010101";
        int numOps = 0;
        int result = solution.minLength(s, numOps);
        assertEquals(1, result);
    }
}