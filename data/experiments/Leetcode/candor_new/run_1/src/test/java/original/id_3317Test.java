package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3317.
*/
class Solution3317Test {
    @Test
    void testNumberOfWays_SimpleCase() {
        Solution3317 solution = new Solution3317();
        int result = solution.numberOfWays(1, 2, 3);
        assertEquals(6, result);
    }
}