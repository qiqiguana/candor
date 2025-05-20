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
public void testIntersection_NoIntersection() {
    List<Integer> interval1 = new ArrayList<>(List.of(-2, 4));
    List<Integer> interval2 = new ArrayList<>(List.of(5, 6));
    String result = Intersection.intersection(interval1, interval2);
    assertEquals("NO", result);
}
}