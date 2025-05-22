package original;

import original.ByLength;
import java.util.Arrays;
import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ByLength.
*/
class ByLengthTest {
    @Test
    void testByLength_EmptyArray_ReturnsEmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, ByLength.byLength(input));
    }
    
    @Test
        public void testNothing(){
            ByLength s = new ByLength();
            }
    @Test
    public void testByLength_EmptyArray() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_SingleElementArray() {
        List<Object> input = Arrays.asList(5);
        List<Object> expected = Arrays.asList("Five");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_OutOfRangeElements() {
        List<Object> input = Arrays.asList(1, -1, 55, 2);
        List<Object> expected = Arrays.asList("Two", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_MultipleElementsArray() {
        List<Object> input = Arrays.asList(1, 2, 3);
        List<Object> expected = Arrays.asList("Three", "Two", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_NonIntegerElements_Fixed_1() {
        List<Object> input = Arrays.asList(1, 'a', 3.5, 2);
        List<Object> expected = Arrays.asList("Two", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_NullInput() {
        List<Object> input = null;
        assertThrows(NullPointerException.class, () -> ByLength.byLength(input));
    }
    @Test
    void testEmptyArray() {
        List<Object> input = new ArrayList<>();
        assertEquals(Collections.emptyList(), ByLength.byLength(input));
    }
    @Test
    void testSingleElementArray() {
        List<Object> input = Arrays.asList(5);
        assertEquals(Arrays.asList("Five"), ByLength.byLength(input));
    }
    @Test
    void testMultipleElementArray() {
        List<Object> input = Arrays.asList(2, 1, 4, 8, 3);
        assertEquals(Arrays.asList("Eight", "Four", "Three", "Two", "One"), ByLength.byLength(input));
    }
    @Test
    void testEdgeCaseZero() {
        List<Object> input = Arrays.asList(0);
        assertEquals(Collections.emptyList(), ByLength.byLength(input));
    }
    @Test
    void testEdgeCaseTen() {
        List<Object> input = Arrays.asList(10);
        assertEquals(Collections.emptyList(), ByLength.byLength(input));
    }
    @Test
    void testIgnoreNonIntegerValues() {
        List<Object> input = Arrays.asList(1, -1, 55);
        assertEquals(Arrays.asList("One"), ByLength.byLength(input));
    }
    @Test
    public void TestByLength_HappyPath_SortedReversedArray_2() {
    	List<Object> arr = new ArrayList<>();
    	arr.add(2);
    	arr.add(1);
    	arr.add(1);
    	arr.add(4);
    	arr.add(5);
    	arr.add(8);
    	arr.add(2);
    	arr.add(3);
    	List<Object> expected = new ArrayList<>();
    	expected.add("Eight");
    	expected.add("Five");
    	expected.add("Four");
    	expected.add("Three");
    	expected.add("Two");
    	expected.add("Two");
    	expected.add("One");
    	expected.add("One");
    	assertEquals(expected, ByLength.byLength(arr));
    }
    @Test
    public void TestByLength_MultipleDigits_1() {
        List<Object> arr = new ArrayList<>();
        arr.add(9);
        arr.add(4);
        arr.add(8);
        List<Object> expected = new ArrayList<>();
        expected.add("Nine");
        expected.add("Eight");
        expected.add("Four");
        assertEquals(expected, ByLength.byLength(arr));
    }
    @Test
    public void TestByLength_SingleElementArray_1() {
    	List<Object> arr = new ArrayList<>();
    	arr.add(5);
    	List<Object> expected = new ArrayList<>();
    	expected.add("Five");
    	assertEquals(expected, ByLength.byLength(arr));
    }
    @Test
    public void TestByLength_NoValidDigits() {
        List<Object> arr = new ArrayList<>();
        arr.add(-1);
        arr.add(0);
        arr.add(10);
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, ByLength.byLength(arr));
    }
    @Test
    public void TestByLength_MultipleInvalid() {
        List<Object> arr = new ArrayList<>();
        arr.add(-1);
        arr.add("abc");
        arr.add(3.5);
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, ByLength.byLength(arr));
    }
                                    
}