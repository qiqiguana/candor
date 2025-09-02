package oracle1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Pluck.
*/
class PluckTest {
    @Test
    void test_pluck_ReturnsSmallestEvenValueAndItsIndex() {
        List<Object> arr = new ArrayList<>();
        arr.add(5);
        arr.add(0);
        arr.add(3);
        arr.add(0);
        arr.add(4);
        arr.add(2);
        List<Object> result = Pluck.pluck(arr);
        assertEquals(0, result.get(0));
        assertEquals(1, result.get(1));
    }
    
    @Test
        void testNothing(){
            Pluck s = new Pluck();
            }
    @Test
    public void testPluckFunctionalityWithEvenValues() {
        List<Object> input = new ArrayList<>();
        input.add(4);
        input.add(2);
        input.add(3);
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        assertEquals(expected, Pluck.pluck(input));
    }
    @Test
    public void testPluckFunctionalityWithNoEvenValues() {
        List<Object> input = new ArrayList<>();
        input.add(7);
        input.add(9);
        input.add(7);
        input.add(1);
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Pluck.pluck(input));
    }
    @Test
    public void testPluckFunctionalityWithEmptyInput() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Pluck.pluck(input));
    }
    @Test
    public void testPluckFunctionalityWithSingleElementInput() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(0);
        assertEquals(expected, Pluck.pluck(input));
    }
    @Test
    public void testPluckFunctionalityWithNonIntegerValues() {
        List<Object> input = new ArrayList<>();
        input.add(4);
        input.add('a');
        input.add(2);
        input.add(null);
        input.add(3);
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(2);
        assertEquals(expected, Pluck.pluck(input));
    }
                                    
}