package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2243.
*/
class Solution2243Test {
    @Test
    void digitSum() {
        Solution2243 solution = new Solution2243();
        String result = solution.digitSum("1111122222", 3);
        assertEquals("132", result);
    }
}