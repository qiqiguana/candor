package original;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Maximum1.
*/
class Maximum1Test {
    @Test
    public void testMaximum_WithArrayAndK_ReturnsCorrectList() {
        List<Integer> arr = Arrays.asList(4, -4, 4);
        int k = 2;
        List<Object> expected = Arrays.asList(4, 4);
        List<Object> actual = Maximum1.maximum(arr, k);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            Maximum1 s = new Maximum1();
            }
    @Test
    public void test_maximum_EmptyArray_K_0() {
        List<Integer> input = new ArrayList<>();
        int k = 0;
        List<Object> result = Maximum1.maximum(input, k);
        assertTrue(result.isEmpty());
    }
    @Test
    public void test_maximum_N_LessThanEqual_K() {
        List<Integer> input = Arrays.asList(4, -4, 4);
        int k = 3;
        List<Object> result = Maximum1.maximum(input, k);
        assertEquals(Arrays.asList(-4, 4, 4), result);
    }
    @Test
    public void test_K_0_NonEmptyArray() {
        List<Integer> arr = Arrays.asList(1, 2, 3);
        int k = 0;
        List<Object> result = Maximum1.maximum(arr, k);
        assertTrue(result.isEmpty());
    }
                                    
}