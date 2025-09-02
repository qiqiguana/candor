package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2864.
*/
class Solution2864Test {
    @Test
    void test_maximumOddBinaryNumber() {
        Solution2864 solution = new Solution2864();
        String result = solution.maximumOddBinaryNumber("10101");
        assertEquals(5, result.length());
    }
}