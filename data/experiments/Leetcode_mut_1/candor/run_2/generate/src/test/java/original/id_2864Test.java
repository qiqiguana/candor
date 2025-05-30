package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2864.
*/
class Solution2864Test {
    @Test
    void testMaximumOddBinaryNumber1() {
        Solution2864 solution = new Solution2864();
        String result = solution.maximumOddBinaryNumber("1001");
        assertEquals("1001", result);
    }
}