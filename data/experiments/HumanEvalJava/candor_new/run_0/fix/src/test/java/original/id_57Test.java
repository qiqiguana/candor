package original;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void test_monotonic_increasing() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        assertTrue(Monotonic.monotonic(numbers));
    }
    
    @Test
        public void testNothing(){
            Monotonic s = new Monotonic();
            }
    @Test
    public void TestMonotonicConstantListFixed2() {
        java.util.List<java.lang.Integer> input = new java.util.ArrayList<>(java.util.Arrays.asList(1, 1, 1));
        java.lang.Boolean result = original.Monotonic.monotonic(input);
        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertTrue(result);
    }
    @Test
    public void TestMonotonicityWithSingleElementList() {
        List<Integer> l = new ArrayList<>();
        l.add(1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestNonMonotonicityDecreasingThenIncreasing() {
        List<Integer> l = new ArrayList<>();
        l.add(2);
        l.add(1);
        l.add(3);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void TestNonMonotonicityIncreasingThenDecreasing() {
        List<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(0);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void testNonMonotonicListWithDecreasingThenIncreasing() {
        List<Integer> input = List.of(1, 0, -1, -2, -3, 4);
        assertTrue(Monotonic.monotonic(input));
    }
                                    
}