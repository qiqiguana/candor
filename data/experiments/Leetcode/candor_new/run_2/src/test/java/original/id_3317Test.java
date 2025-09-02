package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3317.
*/
class Solution3317Test {

    @Test
    void test_numberOfWays() {
        Solution3317 solution = new Solution3317();
        assertEquals(4, solution.numberOfWays(2, 1, 4));
    }
}
