package original;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Maximum1.
*/
class Maximum1Test {
    @Test
    void testMaximum() {
        List<Integer> arr = new ArrayList<>(Arrays.asList(4, -4, 4));
        int k = 2;
        Object[] expected = {4, 4};
        Object[] actual = Maximum1.maximum(arr, k).toArray();
        assertArrayEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            Maximum1 s = new Maximum1();
            }
    @Test
    public void testMaxWithKEqualTo0AndEmptyList() {
        List<Integer> numbers = new ArrayList<>();
        List<java.lang.Object> result = Maximum1.maximum(numbers, 0);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testMaxWithEmptyList() {
        List<Integer> numbers = new ArrayList<>();
        List<Object> result = Maximum1.maximum(numbers, 3);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testMaxWithLargeInput_2() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            numbers.add(i);
        }
        List<Object> result = Maximum1.maximum(numbers, 3);
        assertEquals(Arrays.asList((Object) 998, (Object) 999, (Object) 1000), result);
    }
    @Test
    public void testMaxWithKGreaterThanN() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        List<Object> result = Maximum1.maximum(numbers, 5);
        assertEquals(Arrays.asList(1, 2, 3), result);
    }
    @Test
    public void testMaxWithKGreaterThanNUUnique1() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        List<Object> result = Maximum1.maximum(numbers, 5);
        assertEquals(Arrays.asList(1, 2, 3), result);
    }
                                    
}