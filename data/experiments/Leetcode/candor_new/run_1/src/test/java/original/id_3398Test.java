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
        int result = solution.minLength("00110", 1);
        assertEquals(2, result);
    }
}