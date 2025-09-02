package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Intersection.
*/
class IntersectionTest {
    @Test
    void testIntersectionNonPrimeLength() {
        List<Integer> interval1 = new ArrayList<>();
        interval1.add(1);
        interval1.add(2);
        List<Integer> interval2 = new ArrayList<>();
        interval2.add(2);
        interval2.add(3);
        assertEquals("NO", Intersection.intersection(interval1, interval2));
    }
}