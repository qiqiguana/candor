package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2409.
*/
class Solution2409Test {
    @Test
    void testCountDaysTogether1() {
        Solution2409 solution = new Solution2409();
        assertEquals(3, solution.countDaysTogether("08-15", "08-18", "08-16", "08-19"));
    }
}