package original;

import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2513.
*/
class Solution2513Test {
    @Test
    void testMinimizeSet() {
        Solution2513 solution = new Solution2513();
        int result = solution.minimizeSet(2, 7, 1, 3);
        assertEquals(4, result);
    }
}