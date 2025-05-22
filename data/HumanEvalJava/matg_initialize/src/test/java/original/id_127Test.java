package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Intersection.
*/
class IntersectionTest {
    @Test
    void testIntersection() {
        List<Integer> interval1 = List.of(-3, -1);
        List<Integer> interval2 = List.of(-5, 5);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("YES", result);
    }
}