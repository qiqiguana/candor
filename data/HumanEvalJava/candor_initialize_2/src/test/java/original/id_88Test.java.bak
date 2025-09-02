package original;

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
    void testSortArray_withOddSum_shouldReturnSortedInAscendingOrder() {
        // Arrange
        List<Object> array = new ArrayList<>();
        array.add(2);
        array.add(4);
        array.add(3);
        array.add(0);
        array.add(1);
        array.add(5);

        // Act
        List<Object> result = SortArray1.sortArray(array);

        // Assert
        assertEquals(6, result.size());
        assertEquals(0, result.get(0));
        assertEquals(1, result.get(1));
        assertEquals(2, result.get(2));
        assertEquals(3, result.get(3));
        assertEquals(4, result.get(4));
        assertEquals(5, result.get(5));
    }
    @Test
    void test_empty_array_should_return_empty_array() {
        List<Object> actual = SortArray1.sortArray(new ArrayList<>());
        assertEquals(0, actual.size());
    }
    @Test
    void test_single_element_array_should_return_same_array() {
        List<Object> actual = SortArray1.sortArray(List.of(5));
        assertEquals(List.of(5), actual);
    }
    @Test
    void test_array_with_two_elements_should_sort_correctly() {
        List<Object> actual = SortArray1.sortArray(List.of(2, 1));
        assertEquals(List.of(1, 2), actual);
    }
    @Test
    void test_array_with_duplicate_elements_should_sort_correctly() {
        List<Object> actual = SortArray1.sortArray(List.of(4, 2, 9, 6, 5, 1, 8, 3, 7));
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), actual);
    }
    @Test
    public void SortArray_EmptyList_ReturnsEmptyList() {
        List<Object> input = new ArrayList<>();
        assertEquals(new ArrayList<>(), SortArray1.sortArray(input));
    }
    @Test
    public void SortArray_NullList_ThrowsNullPointerException() {
        List<Object> input = null;
        assertThrows(NullPointerException.class, () -> SortArray1.sortArray(input));
    }
    @Test
    void testSortArrayWithEmptyArray() {
        List<Object> array = new ArrayList<>();
        assertEquals(Collections.emptyList(), SortArray1.sortArray(array));
    }
}