package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2268.
*/
class Solution2268Test {
    @Test
    void testMinimumKeypresses() {
        Solution2268 solution = new Solution2268();
        String s = "apple";
        int expected = 5;
        int actual = solution.minimumKeypresses(s);
        assertEquals(expected, actual);
    }
}