package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1154.
*/
class Solution1154Test {
    @Test
    void testDayOfYear_LeapYear_February29() {
        Solution1154 solution = new Solution1154();
        int result = solution.dayOfYear("2020-02-29");
        assertEquals(60, result);
    }
}
