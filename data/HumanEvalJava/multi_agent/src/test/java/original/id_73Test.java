package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SmallestChange.
*/
class SmallestChangeTest {
    @Test
    void testSmallestChangeShouldReturnZeroForPalindromeArray() {
        List<Integer> arr = List.of(1, 2, 3, 2, 1);
        assertEquals(0, SmallestChange.smallestChange(arr));
    }
    @Test
    void testSmallestChange_not_palindromic() {
        List<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(5);
        arr.add(4);
        arr.add(7);
        arr.add(9);
        arr.add(6);
        int expected = 4;
        int result = SmallestChange.smallestChange(arr);
        assertEquals(expected, result);
    }
    @Test
    void testSmallestChange_palindromic() {
        List<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(2);
        arr.add(1);
        int expected = 0;
        int result = SmallestChange.smallestChange(arr);
        assertEquals(expected, result);
    }
    @Test
    void testSmallestChange_single_element() {
        List<Integer> arr = new ArrayList<>();
        arr.add(1);
        int expected = 0;
        int result = SmallestChange.smallestChange(arr);
        assertEquals(expected, result);
    }
    @Test
    void testSmallestChange_two_elements() {
        List<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        int expected = 1;
        int result = SmallestChange.smallestChange(arr);
        assertEquals(expected, result);
    }
    @Test
    void testClassInstantiation() {
        assertDoesNotThrow(() -> new SmallestChange());
    }
    @Test
    public void smallestChange_test1() {
        List<Integer> arr = List.of(1, 2, 3, 5, 4, 7, 9, 6);
        int expected = 4;
        int actual = SmallestChange.smallestChange(arr);
        assertEquals(expected, actual);
    }
    @Test
    public void smallestChange_test2() {
        List<Integer> arr = List.of(1, 2, 3, 4, 3, 2, 2);
        int expected = 1;
        int actual = SmallestChange.smallestChange(arr);
        assertEquals(expected, actual);
    }
    @Test
    public void smallestChange_test3() {
        List<Integer> arr = List.of(1, 4, 2);
        int expected = 1;
        int actual = SmallestChange.smallestChange(arr);
        assertEquals(expected, actual);
    }
    @Test
    public void smallestChange_test4() {
        List<Integer> arr = List.of(1, 4, 4, 2);
        int expected = 1;
        int actual = SmallestChange.smallestChange(arr);
        assertEquals(expected, actual);
    }
    @Test
    public void smallestChange_test5() {
        List<Integer> arr = List.of(1, 2, 3, 2, 1);
        int expected = 0;
        int actual = SmallestChange.smallestChange(arr);
        assertEquals(expected, actual);
    }
    @Test
    public void smallestChange_test6() {
        List<Integer> arr = List.of(3, 1, 1, 3);
        int expected = 0;
        int actual = SmallestChange.smallestChange(arr);
        assertEquals(expected, actual);
    }
    @Test
    public void smallestChange_test7() {
        List<Integer> arr = List.of(1);
        int expected = 0;
        int actual = SmallestChange.smallestChange(arr);
        assertEquals(expected, actual);
    }
    @Test
    public void smallestChange_test8() {
        List<Integer> arr = List.of(0, 0);
        int expected = 0;
        int actual = SmallestChange.smallestChange(arr);
        assertEquals(expected, actual);
    }
}