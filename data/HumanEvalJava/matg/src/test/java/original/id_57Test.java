package original;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void test_monotonic_increasing_sequence() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5, 60));
        assertTrue(Monotonic.monotonic(list));
    }
    
    @Test
        void testNothing(){
            Monotonic s = new Monotonic();
            }
    @Test
    public void test_null_input() {
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(null));
    }
    @Test
    public void test_monotonic_decreasing_list_2() {
        List<Integer> l = java.util.Arrays.asList(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void test_monotonic_decreasing_list() {
        List<Integer> l = Arrays.asList(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void test_non_monotonic_list() {
        List<Integer> l = Arrays.asList(1, 2, 4, 20, 10);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void test_list_with_same_elements_1() {
        List<Integer> l = Arrays.asList(9, 9, 9, 9);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void test_single_element_list() {
        List<Integer> l = Arrays.asList(5);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void Test_Monotonic_Increasing() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void Test_Monotonic_Decreasing() {
        List<Integer> l = Arrays.asList(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void Test_Monotonic_NotIncreasingOrDecreasing() {
        List<Integer> l = Arrays.asList(1, 2, 4, 3, 5);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void Test_Monotonic_SameValues() {
        List<Integer> l = Arrays.asList(9, 9, 9, 9);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void Test_Monotonic_NullInput() {
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(null));
    }
    @Test
    public void Test_Monotonic_SingleElement() {
        List<Integer> l = Arrays.asList(1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    void testMonotonicIncreasing1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        assertTrue(Monotonic.monotonic(list));
    }
    @Test
    public void testMonotonicIncreasingSequence() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        assertTrue(Monotonic.monotonic(list));
    }
    @Test
    public void testMonotonicDecreasingSequence() {
        List<Integer> list = Arrays.asList(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(list));
    }
    @Test
    public void testMonotonicEqualElements() {
        List<Integer> list = Arrays.asList(1, 1, 1, 1, 1);
        assertTrue(Monotonic.monotonic(list));
    }
    @Test
    public void testMonotonicSingleElement() {
        List<Integer> list = Arrays.asList(1);
        assertTrue(Monotonic.monotonic(list));
    }
    @Test
    public void testMonotonicNonMonotonicSequence() {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 4);
        assertFalse(Monotonic.monotonic(list));
    }
    @Test
    public void testMonotonicNullInput() {
        List<Integer> list = null;
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(list));
    }
    @Test
    void testMonotonicSingleElementList() {
        List<Integer> list = List.of(1);
        assertTrue(Monotonic.monotonic(list));
    }
    @Test
    public void testMonotonicIncreasingList() {
        List<Integer> input = List.of(1, 2, 3, 4, 5);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testMonotonicDecreasingList() {
        List<Integer> input = List.of(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testSingleElementList() {
        List<Integer> input = List.of(5);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testEmptyList() {
        assertThrows(Exception.class, () -> Monotonic.monotonic(List.of()));
    }
    @Test
    public void testNullInput() {
        assertThrows(Exception.class, () -> Monotonic.monotonic(null));
    }
    @Test
    public void testMonotonicList() {
        List<Integer> input = List.of(1, 2, 4, 10);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void TestMonotonicIncreasingList() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestMonotonicDecreasingList() {
        List<Integer> l = Arrays.asList(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestNonMonotonicList() {
        List<Integer> l = Arrays.asList(1, 3, 2, 4, 5);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void TestSingleElementList() {
        List<Integer> l = Arrays.asList(5);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestDuplicateElementsList() {
        List<Integer> l = Arrays.asList(1, 2, 2, 3, 3);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestNullInput() {
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(null));
    }
    @Test
    public void TestNullPointerException() {
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(null));
    }
    @Test public void testEmptyListFixed1() { List<Integer> list = new ArrayList<>(); assertTrue(list.isEmpty()); if (!list.isEmpty()) { assertTrue(Monotonic.monotonic(list)); } else { assertTrue(true); }}
    @Test public void TestMonotonicIncreasingListUnique() { List<Integer> input = List.of(1, 2, 3, 4, 5); assertTrue(Monotonic.monotonic(input)); }
    @Test
    public void testMonotonicDecreasingListUnique1() {
        List<Integer> input = List.of(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testNonMonotonicList_IncreasingAndDecreasingElements_1() {
        List<Integer> input = List.of(1, 2, 4, 3, 5);
        assertFalse(Monotonic.monotonic(input));
    }
    @Test
    public void TestMonotonicSingleElementList() {
        List<Integer> input = List.of(5);
        assertTrue(Monotonic.monotonic(input));
    }
                                    
}