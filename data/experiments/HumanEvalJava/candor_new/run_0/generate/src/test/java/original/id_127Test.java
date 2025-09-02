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
    
    @Test
        public void testNothing(){
            Intersection s = new Intersection();
            }
    @Test
    public void TestIntersectionWithPrimeLength21() {
        java.util.List<java.lang.Integer> interval1 = java.util.Arrays.asList(-3, -1);
        java.util.List<java.lang.Integer> interval2 = java.util.Arrays.asList(-5, 5);
        org.junit.jupiter.api.Assertions.assertEquals("YES", original.Intersection.intersection(interval1, interval2));
    }
    @Test
    public void TestIntersectionWithZeroLength41() {
        java.util.List<java.lang.Integer> interval1 = java.util.Arrays.asList(1, 2);
        java.util.List<java.lang.Integer> interval2 = java.util.Arrays.asList(2, 3);
        org.junit.jupiter.api.Assertions.assertEquals("NO", original.Intersection.intersection(interval1, interval2));
    }
                                    
}