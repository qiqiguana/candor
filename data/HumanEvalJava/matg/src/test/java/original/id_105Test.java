package original;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testByLength_SortAndReverseArray_ExpectedResult() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(1);
        input.add(1);
        input.add(4);
        input.add(5);
        input.add(8);
        input.add(2);
        input.add(3);

        List<Object> expected = new ArrayList<>();
        expected.add("Eight");
        expected.add("Five");
        expected.add("Four");
        expected.add("Three");
        expected.add("Two");
        expected.add("Two");
        expected.add("One");
        expected.add("One");

        assertEquals(expected, ByLength.byLength(input));
    }
    
    @Test
        void testNothing(){
            ByLength s = new ByLength();
            }
    @Test
    public void testEmptyArray() {
    	List<Object> input = new ArrayList<>();
    	List<Object> expected = new ArrayList<>();
    	assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testNullInput() {
    	assertThrows(NullPointerException.class, () -> ByLength.byLength(null));
    }
    @Test
    public void testArrayWithInvalidElementsFixed() {
        List<Object> input = Arrays.asList(1, -1, 55);
        List<Object> expected = Collections.singletonList("One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testArrayWithEdgeCaseElementsCorrected() {
        List<Object> input = Arrays.asList(9, 4, 8);
        List<Object> expected = Arrays.asList("Nine", "Eight", "Four");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testSingleElementArray() {
        List<Object> input = Arrays.asList(5);
        List<Object> expected = Arrays.asList("Five");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testEdgeCaseArray() {
        List<Object> input = Arrays.asList(9, 4, 8);
        List<Object> expected = Arrays.asList("Nine", "Eight", "Four");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testEdgeCaseArray1() {
        List<Object> input = Arrays.asList(1, -1, 55);
        List<Object> expected = Arrays.asList("One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testEmptyArrayFixed() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = Collections.emptyList();
        assertIterableEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testMultipleElementsArrayCorrected() {
        List<Object> input = Arrays.asList(2, 1, 9);
        List<Object> expectedInDescendingOrder = Arrays.asList("Nine", "Two", "One");
        assertEquals(expectedInDescendingOrder, ByLength.byLength(input));
    }
    @Test
    public void testByLength_EmptyArray() {
        List<Object> input = new ArrayList<>();
        List<Object> expectedOutput = new ArrayList<>();
        assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLength_SingleElement() {
        List<Object> input = Arrays.asList(5);
        List<Object> expectedOutput = Arrays.asList("Five");
        assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLength_MultipleElements() {
        List<Object> input = Arrays.asList(2, 4, 8);
        List<Object> expectedOutput = Arrays.asList("Eight", "Four", "Two");
        assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLength_OutOfRangeValues() {
        List<Object> input = Arrays.asList(2, -1, 55);
        List<Object> expectedOutput = Arrays.asList("Two");
        assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLength_DuplicateValues() {
        List<Object> input = Arrays.asList(2, 2, 4, 8);
        List<Object> expectedOutput = Arrays.asList("Eight", "Four", "Two", "Two");
        assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLength_NonIntegerValues() {
        List<Object> input = Arrays.asList(2, 'a', 4.5);
        List<Object> expectedOutput = Arrays.asList("Two");
        assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testMultipleElementsArrayCorrected_2() {
        List<Object> input = Arrays.asList(9, 1, 2);
        List<String> expectedInDescendingOrder = Arrays.asList("Nine", "Two", "One");
        assertEquals(expectedInDescendingOrder, ByLength.byLength(input));
    }
    @Test
    public void testEdgeCaseMinValue() {
        List<Object> input = Arrays.asList(1);
        List<Object> expected = Arrays.asList("One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testEdgeCaseMaxValue() {
        List<Object> input = Arrays.asList(9);
        List<Object> expected = Arrays.asList("Nine");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testDuplicateValues() {
        List<Object> input = Arrays.asList(2, 1, 4, 5, 8, 2);
        List<Object> expected = Arrays.asList("Eight", "Five", "Four", "Two", "Two", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testDuplicateValues1() {
        List<Object> input = Arrays.asList(2, 1, 4, 5, 8, 2);
        List<Object> expected = Arrays.asList("Eight", "Five", "Four", "Two", "Two", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testEdgeCaseMinValue1() {
        List<Object> input = Arrays.asList(1);
        List<Object> expected = Arrays.asList("One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    void testNothing1(){
        assertEquals(Collections.emptyList(), ByLength.byLength(new ArrayList<>()));
    }
    @Test
    public void testByLength_MultipleElements_HappyPath() {
        List<Object> input = Arrays.asList(2, 1, 1, 4, 5, 8, 2, 3);
        List<Object> expected = Arrays.asList("Eight", "Five", "Four", "Three", "Two", "Two", "One", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_EmptyList_Negative() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_DuplicateElements_SpecificFunctionality() {
        List<Object> input = Arrays.asList(2, 2, 3, 4, 5, 6);
        List<Object> expected = Arrays.asList("Six", "Five", "Four", "Three", "Two", "Two");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_OneElement_EdgeCase() {
        List<Object> input = Arrays.asList(5);
        List<Object> expected = Arrays.asList("Five");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_InvalidElement_EdgeCase() {
        List<Object> input = Arrays.asList(1, -1, 55);
        List<Object> expected = Arrays.asList("One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    void testEmptyList(){
        List<Object> result = ByLength.byLength(new ArrayList<>());
        org.junit.jupiter.api.Assertions.assertTrue(result.isEmpty());
    }
    @Test
    void ByLength_EmptyArray_ReturnsEmptyArray() {
        List<Object> result = ByLength.byLength(new ArrayList<>());
        assertEquals(0, result.size());
    }
    @Test
    void ByLength_SingleElementArray_ReturnsSingleElementArray() {
        List<Object> result = ByLength.byLength(new ArrayList<>(Arrays.asList(5)));
        assertEquals("Five", result.get(0));
    }
    @Test
    void ByLength_ArrayWithElementsOutsideRange_IgnoresElementsOutsideRange() {
        List<Object> result = ByLength.byLength(new ArrayList<>(Arrays.asList(1, -1, 55)));
        assertEquals("One", result.get(0));
    }
    @Test
    void ByLength_NullInput_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ByLength.byLength(null));
    }
    @Test
    void ByLength_MultipleElementsArray_ReturnsMultipleElementArray() {
        List<Object> result = ByLength.byLength(new ArrayList<>(Arrays.asList(2, 1, 1, 4, 5, 8, 2, 3)));
        assertEquals("Eight", result.get(0));
        assertEquals("Five", result.get(1));
    }
    @Test
    public void testEmptyListCorrected() {
        List<Object> result = ByLength.byLength(new ArrayList<>());
        assertEquals(0, result.size());
    }
    @Test
    public void testEmptyInput() {
    	List<Object> result = ByLength.byLength(new ArrayList<>());
    	assertTrue(result.isEmpty());
    }
    @Test
    public void testMultipleElementsInput() {
    	List<Object> input = new ArrayList<>();
    	input.add(2);
    	input.add(1);
    	input.add(3);
    	List<Object> result = ByLength.byLength(input);
    	assertEquals(3, result.size());
    	assertEquals("Three", result.get(0));
    	assertEquals("Two", result.get(1));
    	assertEquals("One", result.get(2));
    }
    @Test
    public void testNonIntegerInput() {
        List<Object> input = new ArrayList<>();
        input.add("element");
        List<Object> result = ByLength.byLength(input);
        assertEquals(0, result.size());
    }
    @Test
    public void testByLength_SingleElementArray() {
        List<Object> input = Arrays.asList(5);
        List<Object> expected = Arrays.asList("Five");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_MultipleElementsArray() {
        List<Object> input = Arrays.asList(2, 1, 8, 3);
        List<Object> expected = Arrays.asList("Eight", "Three", "Two", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_IgnoreOutOfRangeValues() {
        List<Object> input = Arrays.asList(1, 10, 3, -2);
        List<Object> expected = Arrays.asList("Three", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testNoExceptionWithInvalidInputType11() {
        List<Object> arr = new ArrayList<>();
        arr.add(123);
        assertDoesNotThrow(() -> ByLength.byLength(arr));
    }
    @Test
    public void testInvalidInputType12Fixed() {
        Object[] arr = new Object[]{5};
        List<Object> result = ByLength.byLength(Arrays.asList(arr));
        assertEquals(Collections.singletonList("Five"), result);
    }
    @Test
    public void testByLength_EmptyArray_1() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = Collections.emptyList();
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLength_IgnoreNonIntegerValues() {
        List<Object> input = Arrays.asList(1, 3.5, 2);
        List<Object> expected = Arrays.asList("Two", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testByLengthWithValidInput() {
    List<Object> input = Arrays.asList(2, 1, 1, 4, 5, 8, 2, 3);
    List<Object> expectedOutput = Arrays.asList("Eight", "Five", "Four", "Three", "Two", "Two", "One", "One");
    assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLengthWithEmptyInput() {
    List<Object> input = new ArrayList<>();
    List<Object> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLengthWithNegativeNumbers() {
    List<Object> input = Arrays.asList(-1, -2, -3);
    List<Object> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLengthWithLargeNumbers() {
    List<Object> input = Arrays.asList(10, 20, 30);
    List<Object> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLengthWithEdgeCases() {
    List<Object> input = Arrays.asList(9, 4, 8);
    List<Object> expectedOutput = Arrays.asList("Nine", "Eight", "Four");
    assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testByLengthWithNoMatchingNumbers() {
    List<Object> input = Arrays.asList(10, 20, 30);
    List<Object> expectedOutput = new ArrayList<>();
    assertEquals(expectedOutput, ByLength.byLength(input));
    }
    @Test
    public void testMultipleElementsArray() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(1);
        input.add(3);
        assertEquals(Arrays.asList("Three", "Two", "One"), ByLength.byLength(input));
    }
    @Test
    public void testArrayWithDuplicateValues() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(2);
        input.add(3);
        assertEquals(Arrays.asList("Three", "Two", "Two"), ByLength.byLength(input));
    }
    @Test
    public void testArrayWithNonIntegerValues() {
        List<Object> input = new ArrayList<>();
        input.add(1.5);
        input.add(2);
        assertEquals(Collections.singletonList("Two"), ByLength.byLength(input));
    }
    @Test
    public void testArrayWithNonIntegerAndNegativeValues() {
        List<Object> input = new ArrayList<>();
        input.add(10);
        input.add(-5);
        assertEquals(Collections.emptyList(), ByLength.byLength(input));
    }
    @Test
    public void testEmptyListAlternative() {
        List<Object> input = new ArrayList<>();
        assertTrue(ByLength.byLength(input).isEmpty());
    }
    @Test
    public void testSingleElementArrayUnique() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        assertEquals(Collections.singletonList("One"), ByLength.byLength(input));
    }
    @Test
    public void testNullInputException() {
        assertThrows(NullPointerException.class, () -> ByLength.byLength(null));
    }
    @Test
    public void testByLengthEmptyArray() {
    	java.util.List<java.lang.Object> arr = new java.util.ArrayList<>();
    	java.util.List<java.lang.Object> expected = new java.util.ArrayList<>();
    	java.util.List<java.lang.Object> actual = ByLength.byLength(arr);
    	org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testMultipleElementsArray_3() {
        java.util.List<java.lang.Object> arr = new java.util.ArrayList<>(java.util.Arrays.asList(1, 3));
        java.util.List<java.lang.Object> expected = java.util.Arrays.asList("Three", "One");
        java.util.List<java.lang.Object> actual = original.ByLength.byLength(arr);
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testIgnoreOutOfRangeElements3() {
        List<Object> arr = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));
        List<String> expected = Arrays.asList("Number is out of range", "Number is out of range", "Number is out of range", "Number is out of range", "Number is out of range");
    
        ByLength.byLength(arr).forEach(x -> assertEquals("Number is out of range", x));
    }
    @Test
    public void testIgnoreNonIntegerInputs() {
        List<Object> input = Arrays.asList(1, 'a', 2, "b", 3);
        List<Object> expected = Arrays.asList("Three", "Two", "One");
        assertEquals(expected, ByLength.byLength(input));
    }
    @Test
    public void testEdgeCaseSingleElementOutOfRange() {
        List<Object> list = new ArrayList<>();
        list.add(10);
        Assertions.assertEquals(Collections.emptyList(), ByLength.byLength(list));
    }
    @Test
    void testMultipleElementArray() {
        List<Object> input = Arrays.asList(2, 1);
        assertEquals(Arrays.asList("Two", "One"), ByLength.byLength(input));
    }
    @Test
    void testArrayWithDuplicateElements() {
        List<Object> input = Arrays.asList(2, 2, 1, 1);
        assertEquals(Arrays.asList("Two", "Two", "One", "One"), ByLength.byLength(input));
    }
    @Test
    void testArrayWithEdgeCases() {
        List<Object> input = Arrays.asList(1, 9);
        assertEquals(Arrays.asList("Nine", "One"), ByLength.byLength(input));
    }
    @Test
    void testArrayWithNegativeNumber() {
        List<Object> input = Arrays.asList(1, -1, 55);
        assertEquals(Arrays.asList("One"), ByLength.byLength(input));
    }
    @Test
    public void byLength_test_1() {
        List<Object> list = Arrays.asList(2, 1, 1, 4, 5, 8, 2, 3);
        List<Object> expected = Arrays.asList("Eight", "Five", "Four", "Three", "Two", "Two", "One", "One");
        assertEquals(expected, ByLength.byLength(list));
    }
    @Test
    void testEmptyArrayAlternative() {
        List<Object> input = new ArrayList<>();
        assertEquals(Collections.emptyList(), ByLength.byLength(input));
    }
    @Test
    void testSingleElementArray_New() {
        List<Object> input = Arrays.asList(5);
        assertEquals(Arrays.asList("Five"), ByLength.byLength(input));
    }
                                    
}