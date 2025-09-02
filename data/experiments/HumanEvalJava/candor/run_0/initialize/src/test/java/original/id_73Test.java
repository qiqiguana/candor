package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SmallestChange.
*/
class SmallestChangeTest {
    @Test
    void testSmallestChange_SimplePalindromicArray_ReturnsZero() {
        List<Integer> arr = new ArrayList<>(List.of(1, 2, 3, 2, 1));
        assertEquals(0, SmallestChange.smallestChange(arr));
    }
    @Test
    public void TestSmallestChange_EmptyList() {
        List<Integer> input = new ArrayList<>();
        int expected = 0;
        int actual = SmallestChange.smallestChange(input);
        assertEquals(expected, actual);
    }
    @Test
    public void TestSmallestChange_NullList() {
        List<Integer> input = null;
        assertThrows(NullPointerException.class, () -> SmallestChange.smallestChange(input));
    }
    @Test
    public void emptyListTest() {
        List<Integer> input = new ArrayList<>();
        int expected = 0;
        assertEquals(expected, SmallestChange.smallestChange(input));
    }
    @Test
    public void smallestChange_EmptyArray() {
        List<Integer> arr = new ArrayList<>();
        assertEquals(0, SmallestChange.smallestChange(arr));
    }
    @Test
    public void testSmallestChangeWithSingleElementArrayFixed2() {
        List<Integer> arr = Arrays.asList(1);
        int expected_result = 0;
        int result = SmallestChange.smallestChange(arr);
        assertEquals(expected_result, result);
    }
    @Test
    public void testSmallestChangeWithTwoElementArray() {
        List<Integer> arr = Arrays.asList(1, 2);
        int expected_result = 1;
        int result = SmallestChange.smallestChange(arr);
        assertEquals(expected_result, result);
    }
    @Test
    public void testSmallestChangeWithEmptyArray() {
        List<Integer> arr = Arrays.asList();
        int expected_result = 0;
        int result = SmallestChange.smallestChange(arr);
        assertEquals(expected_result, result);
    }
    @Test
    public void testSmallestChangeWithNullArray() {
        List<Integer> arr = null;
        assertThrows(NullPointerException.class, () -> SmallestChange.smallestChange(arr));
    }
}