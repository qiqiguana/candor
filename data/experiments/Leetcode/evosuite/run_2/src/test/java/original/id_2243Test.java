package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2243.
*/
class Solution2243Test {

    @Test
    void testDigitSum() {
        Solution2243 solution = new Solution2243();
        String result = solution.digitSum("11111222223", 3);
        assertEquals("135", result);
    }
}