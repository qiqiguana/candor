package original;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpTo.
*/
class CountUpToTest {
    @Test
    void testCountUpTo() {
        List<Object> actual = CountUpTo.countUpTo(5);
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);
        assertEquals(expected, actual);
    }
    
    @Test
        void testNothing(){
            CountUpTo s = new CountUpTo();
            }
    @Test
    public void testCountUpToWithN_0() {
        int n = 0;
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, CountUpTo.countUpTo(n));
    }
    @Test
    public void testCountUpToWithN_1() {
        int n = 1;
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, CountUpTo.countUpTo(n));
    }
    @Test
    public void testCountUpToWithN_20() {
        int n = 20;
        List<Object> expected = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19);
        assertEquals(expected, CountUpTo.countUpTo(n));
    }
                                    
}