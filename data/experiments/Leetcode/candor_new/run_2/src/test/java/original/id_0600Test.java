package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0600.
*/
class Solution0600Test {
    @Test
    void testFindIntegers() {
        Solution0600 solution = new Solution0600();
        int result = solution.findIntegers(8);
        assertEquals(6, result);
    }
}