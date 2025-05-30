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
        assertEquals(0, solution.countDaysTogether("03-15", "08-18", "09-01", "10-19"));
    }
}