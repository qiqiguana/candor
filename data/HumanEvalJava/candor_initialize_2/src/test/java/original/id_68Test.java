package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Pluck.
*/
class PluckTest {
    @Test
void testPluck_SmallestEvenValueWithSmallestIndex_ReturnsCorrectResult() {
        List<Object> input = new ArrayList<>();
        input.add(5);
        input.add(0);
        input.add(3);
        input.add(0);
        input.add(4);
        input.add(2);

        List<Object> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);

        assertEquals(expected, Pluck.pluck(input));
    }
    
    @Test
        public void testNothing(){
            Pluck s = new Pluck();
            }
    @Test
    public void testPluck_with_no_even_values() {
        List<Object> arr = new ArrayList<>();
        arr.add(new Integer(1));
        arr.add(new Integer(3));
        arr.add(new Integer(5));
        List<Object> result = Pluck.pluck(arr);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testPluck_with_a_single_even_value() {
        List<Object> arr = new ArrayList<>();
        arr.add(new Integer(1));
        arr.add(new Integer(2));
        arr.add(new Integer(3));
        List<Object> result = Pluck.pluck(arr);
        assertEquals(2, result.size());
        assertEquals(new Integer(2), result.get(0));
        assertEquals(new Integer(1), result.get(1));
    }
    @Test
    public void testPluck_with_multiple_even_values() {
        List<Object> arr = new ArrayList<>();
        arr.add(new Integer(4));
        arr.add(new Integer(2));
        arr.add(new Integer(3));
        List<Object> result = Pluck.pluck(arr);
        assertEquals(2, result.size());
        assertEquals(new Integer(2), result.get(0));
        assertEquals(new Integer(1), result.get(1));
    }
    @Test
    public void testPluck_with_a_single_node_that_is_not_an_integer() {
        List<Object> arr = new ArrayList<>();
        arr.add("a");
        arr.add(new Integer(4));
        arr.add(new Integer(2));
        arr.add("b");
        List<Object> result = Pluck.pluck(arr);
        assertEquals(2, result.size());
        assertEquals(new Integer(2), result.get(0));
        assertEquals(new Integer(2), result.get(1));
    }
                                    
}