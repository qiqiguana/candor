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
        int expected = 5; // The expected number of integers
        int actual = solution.findIntegers(7);
        assertEquals(expected, actual);
    }
}