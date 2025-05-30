package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void testMonotonic_IncreasingList_ReturnsTrue() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        assertTrue(Monotonic.monotonic(list));
    }
    
    @Test
        public void testNothing(){
            Monotonic s = new Monotonic();
            }
    @Test
    public void Test_Monotonic_With_Single_Element() {
        List<Integer> l = List.of(5);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void Test_Monotonic_With_Increasing_Direction_Change() {
        List<Integer> l = List.of(1, 2, 3, 4, 2);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void Test_Monotonic_With_Decreasing_Direction_Change() {
        List<Integer> l = List.of(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void Test_Monotonic_With_Initial_Zero_Direction() {
        List<Integer> l = List.of(5, 5, 6, 7);
        assertTrue(Monotonic.monotonic(l));
    }
                                    
}