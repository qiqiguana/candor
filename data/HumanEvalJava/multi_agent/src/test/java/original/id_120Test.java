package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Maximum1.
*/
class Maximum1Test {
    @Test
    void test_maximum_with_k_equal_to_array_length() {
        List<Integer> arr = Arrays.asList(-3, -4, 5);
        int k = 3;
        List<Object> expected = Arrays.asList(-4, -3, 5);
        assertEquals(expected, Maximum1.maximum(arr, k));
    }
    @Test
    public void TestMaximumWithSingleElementArray() {
        List<Object> actual = Maximum1.maximum(Arrays.asList(5), 1);
        List<Object> expected = Arrays.asList(5);
        assertEquals(expected, actual);
    }
    public void testMaximumWithDuplicates() {
        List<Integer> arr = Arrays.asList(1, 2, 2, 3, 3, 3);
        int k = 3;
        List<Object> expected = Arrays.asList(2, 3, 3);
        assertEquals(expected, Maximum1.maximum(arr, k));
    }
    public void testMaximumWithNegativeNumbers() {
        List<Integer> arr = Arrays.asList(-1, -2, -3, -4, -5);
        int k = 3;
        List<Object> expected = Arrays.asList(-3, -4, -5);
        assertEquals(expected, Maximum1.maximum(arr, k));
    }
    public void testMaximumWithNGreaterThanK() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5);
        int k = 3;
        List<Object> expected = Arrays.asList(3, 4, 5);
        assertEquals(expected, Maximum1.maximum(arr, k));
    }
    public void testMaximumWithNEqualToK() {
        List<Integer> arr = Arrays.asList(1, 2, 3);
        int k = 3;
        List<Object> expected = Arrays.asList(1, 2, 3);
        assertEquals(expected, Maximum1.maximum(arr, k));
    }
    public void testMaximumWithKEqualToZero() {
        List<Integer> arr = Arrays.asList(1, 2, 3);
        int k = 0;
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Maximum1.maximum(arr, k));
    }
    public void testMaximumWithEmptyArray() {
        List<Integer> arr = new ArrayList<>();
        int k = 3;
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Maximum1.maximum(arr, k));
    }
}