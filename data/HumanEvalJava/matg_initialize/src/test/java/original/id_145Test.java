package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of OrderByPoints.
*/
class OrderByPointsTest {
    @Test
    void testOrderByPoints_EmptyList_ReturnsEmptyList() {
        List<Object> nums = new ArrayList<>();
        List<Object> result = OrderByPoints.orderByPoints(nums);
        assertTrue(result.isEmpty());
    }
}
