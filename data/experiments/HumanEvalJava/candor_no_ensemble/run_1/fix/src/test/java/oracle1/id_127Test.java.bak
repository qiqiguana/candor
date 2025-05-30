package oracle1;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Intersection.
*/
class IntersectionTest {
    @Test
    void testIntersection_NoIntersection() {
        List<Integer> interval1 = new ArrayList<>();
        interval1.add(1);
        interval1.add(2);
        List<Integer> interval2 = new ArrayList<>();
        interval2.add(3);
        interval2.add(4);
        assertEquals("NO", Intersection.intersection(interval1, interval2));
    }
    
    @Test
        void testNothing(){
            Intersection s = new Intersection();
            }
    @Test
    public void testIntersectionOfTwoIntervalsWithPrimeLength2() {
        List<Integer> interval1 = Arrays.asList(-3, -1);
        List<Integer> interval2 = Arrays.asList(-5, 5);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("YES", result);
    }
    @Test
    public void testIntersectingIntervals() {
        List<Integer> interval1 = Arrays.asList(1, 3);
        List<Integer> interval2 = Arrays.asList(2, 4);
        assertEquals("NO", Intersection.intersection(interval1, interval2));
    }
    @Test
    public void testNonIntersectingIntervals() {
        List<Integer> interval1 = Arrays.asList(1, 2);
        List<Integer> interval2 = Arrays.asList(3, 4);
        assertEquals("NO", Intersection.intersection(interval1, interval2));
    }
    @Test
    public void testIntersectingIntervalsWithNegativeNumbers() {
        List<Integer> interval1 = Arrays.asList(-3, -1);
        List<Integer> interval2 = Arrays.asList(-5, 5);
        assertEquals("YES", Intersection.intersection(interval1, interval2));
    }
    @Test
    public void testIntersectingIntervalsWithLargeNumbers() {
        List<Integer> interval1 = Arrays.asList(1000, 2000);
        List<Integer> interval2 = Arrays.asList(1500, 2500);
        assertEquals("NO", Intersection.intersection(interval1, interval2));
    }
    @Test
    void test_intersection_with_no_overlap() {
        List<Integer> interval1 = List.of(-2, 0);
        List<Integer> interval2 = List.of(1, 3);
        assertEquals("NO", Intersection.intersection(interval1, interval2));
    }
                                    
}