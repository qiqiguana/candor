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
    void testIntersection() {
        List<Integer> interval1 = new ArrayList<>();
        interval1.add(-3);
        interval1.add(-1);
        
        List<Integer> interval2 = new ArrayList<>();
        interval2.add(-5);
        interval2.add(5);
        
        assertEquals("YES", Intersection.intersection(interval1, interval2));
    }
    
    @Test
        public void testNothing(){
            Intersection s = new Intersection();
            }
    @Test
    public void intersectionWithStartGreaterThanEnd() {
        List<Integer> interval1 = List.of(1, 2);
        List<Integer> interval2 = List.of(3, 4);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void nonPrimeIntersectionLength() {
        List<Integer> interval1 = List.of(-2, 0);
        List<Integer> interval2 = List.of(-1, 1);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
                                    
}