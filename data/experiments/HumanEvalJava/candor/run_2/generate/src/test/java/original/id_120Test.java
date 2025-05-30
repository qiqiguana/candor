package original;

import java.util.Arrays;
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
    @Test
    public void testEmptyArrayFixed() {
        List<Object> result = Maximum1.maximum(new ArrayList<>(), 0);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testKIsZero() {
        List<Integer> arr = Arrays.asList(5, 15, 0, 3, -13, -8, 0);
        List<Object> result = Maximum1.maximum(arr, 0);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testZeroKFixed() {
        List<Object> result = Maximum1.maximum(Arrays.asList(1, 2, 3), 0);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testNegativeNumbers() {
        List<Object> result = Maximum1.maximum(Arrays.asList(-1, -2, -3), 3);
        assertEquals(result, Arrays.asList(-3, -2, -1));
    }
    @Test
    public void testDuplicateNumbers() {
        List<Object> result = Maximum1.maximum(Arrays.asList(1, 2, 2, 3), 4);
        assertEquals(result.size(), 4);
        assertTrue((int)result.get(0) == 1 || (int)result.get(1) == 1 || (int)result.get(2) == 1 || (int)result.get(3) == 1);
        assertTrue((int)result.get(0) == 2 || (int)result.get(1) == 2 || (int)result.get(2) == 2 || (int)result.get(3) == 2);
        assertTrue((int)result.get(0) == 2 || (int)result.get(1) == 2 || (int)result.get(2) == 2 || (int)result.get(3) == 2);
        assertTrue((int)result.get(0) == 3 || (int)result.get(1) == 3 || (int)result.get(2) == 3 || (int)result.get(3) == 3);
    }
    @Test
    public void testKEqualToArrayLengthCorrected() {
        List<Object> result = Maximum1.maximum(Arrays.asList(1, 2, 3), 3);
        assertEquals(result, Arrays.asList(1, 2, 3));
    }
    @Test
    public void testKGreaterThanArrayLengthFixed1() {
        List<Object> result = Maximum1.maximum(Arrays.asList(1, 2, 3), 4);
        assertEquals(result, Arrays.asList(1, 2, 3));
    }
    @Test
    public void testMaximumWithEmptyArrayAndK0() {
        List<Integer> input = new ArrayList<>();
        int k = 0;
        List<Object> expected = new ArrayList<>();
        Maximum1 max = new Maximum1();
        assertEquals(expected, max.maximum(input, k));
    }
    @Test
    public void testMaximumWithDuplicateElementsAndK21() {
        List<Integer> input = new ArrayList<>();
        input.add(4);
        input.add(-4);
        input.add(4);
        int k = 2;
        List<Object> expected = new ArrayList<>();
        expected.add(4);
        expected.add(4);
        assertEquals(expected, original.Maximum1.maximum(input, k));
    }
    @Test
    public void testMaximumWithNegativeNumbersAndK2_1() {
        List<Integer> input = new ArrayList<>();
        input.add(-3);
        input.add(-4);
        input.add(-5);
        int k = 2;
        List<Object> expected = new ArrayList<>();
        expected.add(-4);
        expected.add(-3);
        assertEquals(expected, original.Maximum1.maximum(input, k));
    }
    @Test
    public void testMaximumWithLargeInputArrayAndK3() {
        List<Integer> input = new ArrayList<>();
        input.add(10);
        input.add(20);
        input.add(30);
        input.add(40);
        input.add(50);
        int k = 3;
        List<Object> expected = new ArrayList<>();
        expected.add(30);
        expected.add(40);
        expected.add(50);
        assertEquals(expected, Maximum1.maximum(input, k));
    }
    @Test
    public void testMaximumWithEdgeCaseK0() {
        List<Integer> input = new ArrayList<>();
        input.add(10);
        input.add(20);
        input.add(30);
        int k = 0;
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, original.Maximum1.maximum(input, k));
    }
                                    
}