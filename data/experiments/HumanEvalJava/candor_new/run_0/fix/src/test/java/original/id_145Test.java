package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

/**
* Test class of OrderByPoints.
*/
class OrderByPointsTest {
    @Test
    public void testOrderByPoints_SimpleList() {
        List<Object> nums = new ArrayList<>();
        nums.add(1);
        nums.add(11);
        nums.add(-1);
        nums.add(-11);
        nums.add(-12);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(-1);
        expected.add(11);
        expected.add(-11);
        expected.add(-12);
    
        assertEquals(expected, OrderByPoints.orderByPoints(nums));
    }
    
    @Test
        public void testNothing(){
            OrderByPoints s = new OrderByPoints();
            }
                                    
}