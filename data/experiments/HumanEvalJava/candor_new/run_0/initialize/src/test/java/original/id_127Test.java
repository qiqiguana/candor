package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Intersection.
*/
class IntersectionTest {
    @Test
    void testIntersection_NoOverlap() {
        List<Integer> interval1 = List.of(1, 2);
        List<Integer> interval2 = List.of(3, 5);
        assertEquals("NO", Intersection.intersection(interval1, interval2));
    }
}