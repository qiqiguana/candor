package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void testMonotonicIncreasing() {
        List<Integer> l = List.of(1, 2, 4, 10);
        assertTrue(Monotonic.monotonic(l));
    }
    
    @Test
        public void testNothing(){
            Monotonic s = new Monotonic();
            }
    @Test
    public void testMonotonicityWithSingleElementList() {
        List<Integer> input = List.of(1);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testMonotonicityWithNoDirectionChange() {
        List<Integer> input = List.of(1, 2, 3, 4);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testNonMonotonicityWithDecreasingSequence() {
        List<Integer> input = List.of(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testNonMonotonicityWithIncreasingSequence() {
        List<Integer> input = List.of(1, 2, 3, 4, 5);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testMonotonicityWithEqualElements() {
        List<Integer> input = List.of(9, 9, 9, 9);
        assertTrue(Monotonic.monotonic(input));
    }
                                    
}