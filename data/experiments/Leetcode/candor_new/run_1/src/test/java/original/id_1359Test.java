package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1359.
*/
class Solution1359Test {
    @Test
    void testCountOrders_SimpleCase() {
        Solution1359 solution = new Solution1359();
        int n = 3;
        int expected = 90;
        assertEquals(expected, solution.countOrders(n));
    }
}