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
        assertNotEquals(result[0], 0);
        assertNotEquals(result[1], 0);
        assertEquals(String.valueOf(result[0]) + String.valueOf(result[1]), "19");
    }
}