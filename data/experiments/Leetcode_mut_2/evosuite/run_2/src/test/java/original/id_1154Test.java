package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1154.
*/
class Solution1154Test {
    @Test
    void testDayOfYear() {
        Solution1154 solution = new Solution1154();
        assertEquals(60, solution.dayOfYear("2003-03-01"));
    }
}