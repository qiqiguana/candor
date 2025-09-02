package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IncrList.
*/
class IncrListTest {

    @Test
    void testIncrList_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        // Act
        List<Object> actual = IncrList.incrList(input);
        // Assert
        assertEquals(expected, actual);
    }
    @Test
    public void testIncrList_EmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrList_NonIntegerValues() {
        List<Object> input = Arrays.asList(1, "a", 3.0);
        List<Object> expected = Arrays.asList(2);
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrList_MultipleElements() {
        List<Object> input = Arrays.asList(1, 2, 3);
        List<Object> expected = Arrays.asList(2, 3, 4);
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrList_NonIntegerElement() {
        List<Object> input = Arrays.asList(1, "a", 3);
        List<Object> expected = Arrays.asList(2, 4);
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrList_LargeList() {
        List<Object> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Object> expected = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrList_NullList() {
        assertThrows(NullPointerException.class, () -> IncrList.incrList(null));
    }
    @Test
    public void testIncrList_with_empty_list() {
        List<Object> input = new ArrayList<>();
        assertEquals(new ArrayList<>(), IncrList.incrList(input));
    }
    @Test
    public void testIncrList_with_single_element() {
        List<Object> input = Arrays.asList(5);
        assertEquals(Arrays.asList(6), IncrList.incrList(input));
    }
    @Test
    public void testIncrList_with_multiple_elements() {
        List<Object> input = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(Arrays.asList(2, 3, 4, 5, 6), IncrList.incrList(input));
    }
    @Test
    public void testIncrList_with_non_integer_elements() {
        List<Object> input = Arrays.asList(1, 'a', 2, 'b', 3);
        assertEquals(Arrays.asList(2, 3, 4), IncrList.incrList(input));
    }
    @Test
    public void testIncrList_with_null_input() {
        assertThrows(NullPointerException.class, () -> IncrList.incrList(null));
    }
    @Test
    public void testIncrList_with_java_util_List_input() {
        List<Object> input = Arrays.asList(1, 2, 3);
        assertEquals(Arrays.asList(2, 3, 4), IncrList.incrList(input));
    }
    @Test
    public void testIncrListWithEmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrListWithSingleElement() {
        List<Object> input = Arrays.asList(5);
        List<Object> expected = Arrays.asList(6);
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrListWithMultipleElements() {
        List<Object> input = Arrays.asList(1, 2, 3);
        List<Object> expected = Arrays.asList(2, 3, 4);
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrListWithDuplicateElements() {
        List<Object> input = Arrays.asList(5, 2, 5, 2, 3, 3, 9, 0, 123);
        List<Object> expected = Arrays.asList(6, 3, 6, 3, 4, 4, 10, 1, 124);
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrListWithNonIntegerElements() {
        List<Object> input = Arrays.asList(5, "a", 2);
        List<Object> expected = Arrays.asList(6, 3);
        assertEquals(expected, IncrList.incrList(input));
    }
    @Test
    public void testIncrListWithNonIntegerElements1() {
        List<Object> input = Arrays.asList("a",5, "b",2);
        List<Object> expected = Arrays.asList(6, 3);
        assertEquals(expected, IncrList.incrList(input));
    }
}