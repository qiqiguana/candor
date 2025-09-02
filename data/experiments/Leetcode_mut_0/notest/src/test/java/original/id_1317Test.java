package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1317.
*/
class Solution1317Test {
    @Test
    void testGetNoZeroIntegers() {
        Solution1317 solution = new Solution1317();
        int[] result = solution.getNoZeroIntegers(10);
        assertEquals(2, result.length);
    }
}