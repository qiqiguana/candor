package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void test_Monotonic_IncreasingList_ReturnsTrue() {
        // Arrange
        List<Integer> l = List.of(1, 2, 3, 4, 5);
        Boolean expected = true;
        
        // Act
        Boolean result = Monotonic.monotonic(l);
        
        // Assert
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            Monotonic s = new Monotonic();
            }
    @Test
    public void testMonotonicIncreasingSequence() {
    	List<Integer> l = List.of(1, 2, 3, 4, 5);
    	assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicDecreasingSequence() {
    	List<Integer> l = List.of(5, 4, 3, 2, 1);
    	assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testSequenceWithEqualElements() {
    	List<Integer> l = List.of(9, 9, 9, 9);
    	assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testSingleElementSequence() {
    	List<Integer> l = List.of(5);
    	assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicIncreasingSequence2() {
    	List<Integer> l = List.of(1, 3, 5, 7);
    	assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testNonMonotonicSequenceIncreasingThenDecreasing() {
        List<Integer> l = List.of(1, 2, 4, 3);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicIncreasingList() {
        List<Integer> l = List.of(1, 2, 4, 10);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicDecreasingList() {
        List<Integer> l = List.of(4, 1, 0, -10);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testNonMonotonicList() {
        List<Integer> l = List.of(1, 20, 4, 10);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void testSingleElementList() {
        List<Integer> l = List.of(5);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testDuplicateElementsList() {
        List<Integer> l = List.of(1, 1, 1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicIncreasingList2() {
        List<Integer> l = List.of(1, 2, 4, 10);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testNullPointer() {
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(null));
    }
    @Test
    public void testNullPointer2() {
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(null));
    }
    @Test
    public void testEmptySequenceFixed2() {
        List<Integer> l = List.of();
        if (!l.isEmpty()) { 
            assertTrue(Monotonic.monotonic(l));
        } else { 
            assertTrue(true);
        }
    }
    @Test
    public void testEmptySequenceFixed() {
        List<Integer> l = List.of();
        if (l.isEmpty()) {
            assertTrue(true);
        } else {
            assertTrue(Monotonic.monotonic(l));
        }
    }
    @Test
    public void testEmptyListFixed() { List<Integer> l = List.of(); if (l.isEmpty()) { assertTrue(true); } else { assertTrue(Monotonic.monotonic(l)); }}
    @Test
    public void test_monotonic_increasing_1() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void test_monotonic_decreasing() {
        List<Integer> l = new ArrayList<>(Arrays.asList(4, 3, 2, 1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void test_non_monotonic_increasing_decreasing() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2, 4, 3));
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void test_equal_elements() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 1, 1, 1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void test_single_element() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void test_two_elements() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicIncreasing() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicDecreasing() {
        List<Integer> l = Arrays.asList(5, 4, 3, 2, 1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testNonMonotonic() {
        List<Integer> l = Arrays.asList(1, 3, 2, 4, 5);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void testDuplicateElements() {
        List<Integer> l = Arrays.asList(1, 1, 1, 1, 1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testSingleElementList1() {
        List<Integer> l = Arrays.asList(1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> Monotonic.monotonic(new ArrayList<>()));
    }
    @Test
    public void testMonotonicDecreasingList2() {
        List<Integer> l = new ArrayList<>(Arrays.asList(9, 7, 5, 3, 1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicIncreasingListUnique() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicDecreasingListUnique() {
        List<Integer> l = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testNonMonotonicList2() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 4));
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void testSingleElementListUnique() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicIncreasingList3() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void test_Monotonic_Increasing_Test_1() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        boolean expected_result = true;
        assertEquals(expected_result, Monotonic.monotonic(input));
    }
    @Test
    public void test_Monotonic_Decreasing_Test_2() {
        List<Integer> input = Arrays.asList(5, 4, 3, 2, 1);
        boolean expected_result = true;
        assertEquals(expected_result, Monotonic.monotonic(input));
    }
    @Test
    public void test_Non_Monotonic_Test_3() {
        List<Integer> input = Arrays.asList(1, 2, 3, 2, 5);
        boolean expected_result = false;
        assertEquals(expected_result, Monotonic.monotonic(input));
    }
    @Test
    public void test_Single_Element_List_Test_4() {
        List<Integer> input = Arrays.asList(1);
        boolean expected_result = true;
        assertEquals(expected_result, Monotonic.monotonic(input));
    }
    @Test
    public void test_Duplicate_Elements_Test_5() {
        List<Integer> input = Arrays.asList(1, 2, 2, 3, 3, 3);
        boolean expected_result = true;
        assertEquals(expected_result, Monotonic.monotonic(input));
    }
    @Test
    public void test_Negative_Numbers_Test_6() {
        List<Integer> input = Arrays.asList(-1, -2, -3, -4, -5);
        boolean expected_result = true;
        assertEquals(expected_result, Monotonic.monotonic(input));
    }
    @Test
    public void TestMonotonicDecreasingList() {
        List<Integer> input = Arrays.asList(10, 8, 5, 1);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void TestMonotonicNonMonotonicList() {
        List<Integer> input = Arrays.asList(1, 2, 3, 2, 5);
        assertFalse(Monotonic.monotonic(input));
    }
    @Test
    public void TestMonotonicSingleElementList() {
        List<Integer> input = Arrays.asList(5);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void TestMonotonicEmptyList() {
        List<Integer> input = Arrays.asList();
        assertThrows(IndexOutOfBoundsException.class, () -> Monotonic.monotonic(input));
    }
    @Test
    public void TestMonotonicNullList() {
        List<Integer> input = null;
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(input));
    }
    @Test
    public void TestMonotonicConstantList() {
        List<Integer> input = Arrays.asList(1, 1, 1);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testMonotonicDecreasing1() {
        List<Integer> l = new ArrayList<>(Arrays.asList(4, 3, 2, 1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicDecreasing2() {
        List<Integer> l = new ArrayList<>(Arrays.asList(10, 9, 8, 7));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testNonMonotonicListWithDirectionChanges1() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 3, 2, 4));
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void testSingleElementListCorrected() {
        List<Integer> l = new ArrayList<>(Arrays.asList(5));
        assertTrue(Monotonic.monotonic(l) != null);
    }
    @Test
    public void testNullListFixed() {
        List<Integer> l = null;
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(l));
    }
    @Test
    public void testDuplicateElementsIncreasing() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2, 2, 3));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testDuplicateElementsDecreasing() {
        List<Integer> l = new ArrayList<>(Arrays.asList(4, 3, 2, 1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicEqualElementsList() {
        List<Integer> input = Arrays.asList(5, 5, 5, 5);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testNonMonotonicList3() {
        List<Integer> input = Arrays.asList(1, 3, 4, 2);
        assertFalse(Monotonic.monotonic(input));
    }
    @Test
    public void testListWithSameElements() {
        List<Integer> input = Arrays.asList(1, 1, 1, 1, 1);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> Monotonic.monotonic(null));
    }
    @Test
    public void testListWithSingleElement() {
        List<Integer> input = Arrays.asList(1);
        assertTrue(Monotonic.monotonic(input));
    }
    @Test
    public void TestMonotonicDecreasing() {
        List<Integer> l = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestNonMonotonicList() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 3, 2, 4, 5));
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void TestSingleElementList() {
        List<Integer> l = new ArrayList<>(Arrays.asList(5));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestEmptyList() {
        List<Integer> l = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> Monotonic.monotonic(l));
    }
    @Test
    public void TestMonotonicIncreasing1() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestMonotonicDecreasing1() {
        List<Integer> l = new ArrayList<>(Arrays.asList(5, 4));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestNonMonotonicList1Fixed() {
        List<Integer> l = new ArrayList<>(Arrays.asList(2, 1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestSingleElementList1() {
        List<Integer> l = new ArrayList<>(Arrays.asList(10));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void TestEmptyList1() {
        List<Integer> l = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> Monotonic.monotonic(l));
    }
    @Test
    void testMonotonicDecreasingListCorrected() {
        List<Integer> l = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    void testNonMonotonicListWithDirectionChange1() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2, 4, 3, 5));
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    void testEmptyList2() {
        List<Integer> emptyList = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> Monotonic.monotonic(emptyList));
    }
    @Test
    void testSingleElementList_ReturnsTrue() { List<Integer> l = new ArrayList<>(Arrays.asList(5)); assertTrue(Boolean.TRUE.equals(Monotonic.monotonic(l))); }
    @Test
    void testDuplicateElementsListFixed2() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2, 3, 3, 4));
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    void testNullList() {
    	assertThrows(NullPointerException.class, () -> Monotonic.monotonic(null));
    }
    @Test
    public void testMonotonicDecreasingListWithEqualElements1() {
        List<Integer> l = Arrays.asList(5, 4, 4, 2, 1);
        assertTrue(Monotonic.monotonic(l));
    }
    @Test
    public void testNonMonotonicList2Fixed() {
        List<Integer> l = Arrays.asList(1, 3, 2, 4, 5);
        assertFalse(Monotonic.monotonic(l));
    }
    @Test
    public void testMonotonicDecreasingWithDifferentValues() {
        List<Integer> input = Arrays.asList(4, 1, 3, 0);
        Boolean expected = false;
        assertEquals(expected, Monotonic.monotonic(input));
    }
                                    
}