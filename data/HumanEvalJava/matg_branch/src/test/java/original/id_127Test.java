package original;

import java.util.Arrays;
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
    
    @Test
        public void testNothing(){
            Intersection s = new Intersection();
            }
    @Test
    public void TestIntersectionWithPrimeLength1() {
        List<Integer> interval1 = Arrays.asList(-3, -1);
        List<Integer> interval2 = Arrays.asList(-5, 5);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("YES", result);
    }
    @Test
    public void TestIntersectionWithNonPrimeLength1() {
        List<Integer> interval1 = Arrays.asList(1, 2);
        List<Integer> interval2 = Arrays.asList(2, 3);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void TestNoIntersection1() {
        List<Integer> interval1 = Arrays.asList(1, 2);
        List<Integer> interval2 = Arrays.asList(3, 5);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void TestNegativeNumbers1() {
        List<Integer> interval1 = Arrays.asList(-11, 2);
        List<Integer> interval2 = Arrays.asList(-1, -1);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void TestEqualIntervals12() {
        List<Integer> interval1 = Arrays.asList(-11, 2);
        List<Integer> interval2 = Arrays.asList(-11, 2);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("YES", result);
    }
    @Test
    public void TestOnePointIntervals1() {
        List<Integer> interval1 = Arrays.asList(-11, -11);
        List<Integer> interval2 = Arrays.asList(-11, -11);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void testIntersectionWithValidIntervals() {
        List<Integer> interval1 = Arrays.asList(1, 3);
        List<Integer> interval2 = Arrays.asList(2, 4);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void testIntersectionWithNoOverlap() {
        List<Integer> interval1 = Arrays.asList(1, 2);
        List<Integer> interval2 = Arrays.asList(3, 5);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void testIntersectionWithNegativeIntervals() {
        List<Integer> interval1 = Arrays.asList(-3, -2);
        List<Integer> interval2 = Arrays.asList(-4, -1);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void testIntersectionWithInvalidIntervals() {
        List<Integer> interval1 = Arrays.asList(5, 2);
        List<Integer> interval2 = Arrays.asList(3, 6);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void testIntersectionWithSinglePoint() {
        List<Integer> interval1 = Arrays.asList(1, 1);
        List<Integer> interval2 = Arrays.asList(1, 2);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void testIntersectingIntervals() {
        List<Integer> interval1 = Arrays.asList(-3, -1);
        List<Integer> interval2 = Arrays.asList(-5, 5);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("YES", result);
    }
    @Test
    public void testNonIntersectingIntervals() {
        List<Integer> interval1 = Arrays.asList(1, 2);
        List<Integer> interval2 = Arrays.asList(3, 5);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void testIdenticalIntervals() {
        List<Integer> interval1 = Arrays.asList(1, 2);
        List<Integer> interval2 = Arrays.asList(1, 2);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void testSingleElementIntervals() {
        List<Integer> interval1 = Arrays.asList(-3, -3);
        List<Integer> interval2 = Arrays.asList(-5, -5);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
    @Test
    public void testZeroLengthIntervals() {
        List<Integer> interval1 = Arrays.asList(0, 0);
        List<Integer> interval2 = Arrays.asList(-3, -2);
        String result = Intersection.intersection(interval1, interval2);
        assertEquals("NO", result);
    }
                                    
}