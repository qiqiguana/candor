package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortArray1.
*/
class SortArray1Test {
    @Test
    void test_sortArray_odd_sum() {
        List<Object> array = new ArrayList<>();
        array.add(2);
        array.add(4);
        array.add(3);
        array.add(0);
        array.add(1);
        array.add(5);
        List<Object> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, SortArray1.sortArray(array));
    }
    @Test
    public void sortArray_EmptyArray_ReturnsEmptyArray() {
        List<Object> input = new ArrayList<>();
        List<Object> expectedOutput = new ArrayList<>();
        assertEquals(expectedOutput, SortArray1.sortArray(input));
    }
    @Test
    public void sortArray_SingleElementArray_ReturnsSameArray() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        List<Object> expectedOutput = new ArrayList<>();
        expectedOutput.add(1);
        assertEquals(expectedOutput, SortArray1.sortArray(input));
    }
    @Test
    public void sortArray_MultipleElementsArray_ReturnsSorted() {
        List<Object> input = new ArrayList<>();
        input.add(3);
        input.add(1);
        input.add(2);
        List<Object> expectedOutput = new ArrayList<>();
        expectedOutput.add(1);
        expectedOutput.add(2);
        expectedOutput.add(3);
        assertEquals(expectedOutput, SortArray1.sortArray(input));
    }
    @Test
    public void sortArray_MultipleElementsArray_DuplicateElements_ReturnsSorted() {
        List<Object> input = new ArrayList<>();
        input.add(3);
        input.add(1);
        input.add(2);
        input.add(2);
        List<Object> expectedOutput = new ArrayList<>();
        expectedOutput.add(1);
        expectedOutput.add(2);
        expectedOutput.add(2);
        expectedOutput.add(3);
        assertEquals(expectedOutput, SortArray1.sortArray(input));
    }
    @Test
    public void sortArray_MultipleElementsArray_NegativeNumbers_ReturnsSorted() {
        List<Object> input = new ArrayList<>();
        input.add(3);
        input.add(-1);
        input.add(2);
        input.add(-4);
        List<Object> expectedOutput = new ArrayList<>();
        expectedOutput.add(-4);
        expectedOutput.add(-1);
        expectedOutput.add(2);
        expectedOutput.add(3);
        assertEquals(expectedOutput, SortArray1.sortArray(input));
    }
    @Test
    public void sortArray_NullInput_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SortArray1.sortArray(null));
    }
    @Test
    public void testEmptyArray1() {
        List<Object> array = new ArrayList<>();
        assertEquals(Collections.emptyList(), original.SortArray1.sortArray(array));
    }
    @Test
    public void sortArray_EmptyList_ReturnsEmptyList() {
        List<Object> input = new ArrayList<>();
        assertEquals(Collections.emptyList(), SortArray1.sortArray(input));
    }
    @Test
    public void sortArray_SingleElementList_ReturnsSameList() {
        List<Object> input = new ArrayList<>(Collections.singletonList(5));
        assertEquals(input, SortArray1.sortArray(input));
    }
    @Test
    public void sortArray_EvenSum_DescendingOrder_Fixed() {
        List<Object> input = new ArrayList<>(Arrays.asList(2, 4, 3, 0, 1, 6));
        List<Object> expected = new ArrayList<>(Arrays.asList(6, 4, 3, 2, 1, 0));
        assertEquals(expected, SortArray1.sortArray(input));
    }
}