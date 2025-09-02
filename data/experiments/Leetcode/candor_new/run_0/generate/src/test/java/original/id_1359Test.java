package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1359.
*/
class Solution1359Test {

    @Test
    void testCountOrders() {
        Solution1359 solution = new Solution1359();
        assertEquals(1, solution.countOrders(1));
    }
    @Test
    public void testCountOrdersReturnsCorrectResults() {
        Solution1359 s = new Solution1359();
        assertEquals(1, s.countOrders(1));
        assertEquals(6, s.countOrders(2));
    }
}