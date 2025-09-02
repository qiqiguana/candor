package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3222.
*/
class Solution3222Test {
    @Test
    void testLosingPlayer_Bob() {
        Solution3222 solution = new Solution3222();
        String result = solution.losingPlayer(10, 16);
        assertEquals("Bob", result);
    }
}