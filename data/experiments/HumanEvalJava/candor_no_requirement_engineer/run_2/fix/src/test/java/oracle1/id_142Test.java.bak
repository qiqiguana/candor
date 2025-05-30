package oracle1;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SumSquares1.
*/
class SumSquares1Test {
 
    @Test
    void testSumSquares_MultipleOfThreeAndFour_ReturnsCorrectResult() {
        List<Object> list = new ArrayList<>();
        list.add(-56);
        list.add(-99);
        list.add(1);
        list.add(0);
        list.add(-2);
        assertEquals(3030, SumSquares1.sumSquares(list));
    }
    @Test
    void test_sumSquares_EmptyList() {
        List<Object> lst = new ArrayList<>();
        assertEquals(0, SumSquares1.sumSquares(lst));
    }
    @Test
    void test_sumSquares_NullInput() {
        List<Object> lst = null;
        assertThrows(NullPointerException.class, () -> SumSquares1.sumSquares(lst));
    }
    @Test
    void test_sumSquares_LargeList_VariousIndexes() {
        List<Object> lst = new ArrayList<>();
        lst.add(-16);
        lst.add(-9);
        lst.add(-2);
        lst.add(36);
        lst.add(36);
        lst.add(26);
        lst.add(-20);
        lst.add(25);
        lst.add(-40);
        lst.add(20);
        lst.add(-4);
        lst.add(12);
        lst.add(-26);
        lst.add(35);
        lst.add(37);
        assertEquals(-14196, SumSquares1.sumSquares(lst));
    }
    @Test
    void testEmptyList() {
    	List<Object> input = new ArrayList<>();
    	int result = SumSquares1.sumSquares(input);
    	assertEquals(0, result);
    }
    @Test
    void testMultipleElementsListWithIndexNotMultipleOf3Or4() {
    	List<Object> input = Arrays.asList(1, 2, 5);
    	int result = SumSquares1.sumSquares(input);
    	assertEquals(8, result);
    }
    @Test
    void testMultipleElementsListWithIndexMultipleOf3() {
    	List<Object> input = Arrays.asList(1, 2, 9);
    	int result = SumSquares1.sumSquares(input);
    	assertEquals(12, result);
    }
    @Test
    void testMultipleElementsListWithNegativeNumbers() {
    	List<Object> input = Arrays.asList(-1, -5, 2, -1, -5);
    	int result = SumSquares1.sumSquares(input);
    	assertEquals(-126, result);
    }
    @Test
    public void testSumOfSquaresWithPositiveNumbers() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        int expected = 6;
        int actual = SumSquares1.sumSquares(input);
        assertEquals(expected, actual);
    }
    @Test
    public void testSumOfSquaresWithZero() {
        List<Object> input = new ArrayList<>();
        input.add(0);
        int expected = 0;
        int actual = SumSquares1.sumSquares(input);
        assertEquals(expected, actual);
    }
    @Test
    public void testSumOfSquaresWithEmptyList() {
        List<Object> input = new ArrayList<>();
        int expected = 0;
        int actual = SumSquares1.sumSquares(input);
        assertEquals(expected, actual);
    }
    @Test
    public void testSumSquares_EmptyList() {
        List<Object> input = new ArrayList<>();
        int result = SumSquares1.sumSquares(input);
        assertEquals(0, result);
    }
    @Test
    public void testSumSquares_NullInput() {
        List<Object> input = null;
        assertThrows(NullPointerException.class, () -> SumSquares1.sumSquares(input));
    }
    @Test
    public void testSumSquares_SingleElementList() {
        List<Object> input = Arrays.asList(1);
        int result = SumSquares1.sumSquares(input);
        assertEquals(1, result);
    }
    @Test
    public void testSumSquares_NonIntegerValue() {
        List<Object> input = Arrays.asList(1, "a", 3);
        assertThrows(ClassCastException.class, () -> SumSquares1.sumSquares(input));
    }
    @Test
    public void testMultipleOf3() {
        List<Object> lst = Arrays.asList(1, 2, 3);
        assertEquals(6, SumSquares1.sumSquares(lst));
    }
    @Test
    public void SumSquares_EmptyList() {
        List<Object> lst = new ArrayList<>();
        assertEquals(0, SumSquares1.sumSquares(lst));
    }
    @Test
    public void SumSquares_SingleElement() {
        List<Object> lst = new ArrayList<>(Arrays.asList(1));
        assertEquals(1, SumSquares1.sumSquares(lst));
    }
    @Test
    public void SumSquares_MultipleOfThree() {
        List<Object> lst = new ArrayList<>(Arrays.asList(3));
        assertEquals(9, SumSquares1.sumSquares(lst));
    }
    @Test
    public void testMultipleElements() {
        List<Object> input = Arrays.asList(-1, -5, 2, -1, -5);
        int expectedResult = -126;
        int result = SumSquares1.sumSquares(input);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testEmptyListUniqueName() {
        List<Object> input = new ArrayList<>();
        int expectedResult = 0;
        int result = SumSquares1.sumSquares(input);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testSingleElement() {
        List<Object> input = Arrays.asList(5);
        int expectedResult = 25;
        int result = SumSquares1.sumSquares(input);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testSquareRoots() {
        List<Object> input = Arrays.asList(1, 2, 9, 4, 5);
        int expectedResult = 153;
        int result = SumSquares1.sumSquares(input);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testSumAndCube() {
        List<Object> input = Arrays.asList(1, 2, 3, 64, 5);
        int expectedResult = 4227;
        int result = SumSquares1.sumSquares(input);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testSumSquares_MultipleElementsList() {
        List<Object> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        lst.add(3);
        assertEquals(6, SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares_NonIntegerElementsList() {
        List<Object> lst = new ArrayList<>();
        lst.add("a");
        lst.add(2);
        lst.add(true);
        assertThrows(ClassCastException.class, () -> SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares_NullList() {
        List<Object> lst = null;
        assertThrows(NullPointerException.class, () -> SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares_NullInputFixed() {
        List<Object> lst = null;
        assertThrows(NullPointerException.class, () -> SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares_SingleElementList_Duplicate() {
        List<Object> lst = new ArrayList<>();
        lst.add(5);
        assertEquals(25, SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares_LargeInputList_Fixed() {
        List<Object> lst = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            lst.add(i);
        }
        long expectedOutput = 2036291054;
        assertEquals(expectedOutput, (long) SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares_ListWithNegativeNumbers_Fixed() {
        List<Object> lst = new ArrayList<>();
        lst.add(-1);
        lst.add(-2);
        lst.add(-3);
        assertEquals(-4, SumSquares1.sumSquares(lst));
    }
    @Test
    public void testZeroList() {
        assertEquals(0, SumSquares1.sumSquares(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0)));
    }
    @Test
    public void testEmptyListAlternative1() {
        assertEquals(0, SumSquares1.sumSquares(new ArrayList<>()));
    }
    @Test
    public void testSingleElementListUniqueId() {
        assertEquals(25, SumSquares1.sumSquares(Arrays.asList(5)));
    }
    @Test
    public void testMultipleElementsListUniqueId2() {
        assertEquals(939, SumSquares1.sumSquares(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }
    @Test
    public void testMultipleElementsListUniqueId1() {
        assertEquals(939, SumSquares1.sumSquares(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }
    @Test
    public void testNegativeNumbersListCorrected() {
        assertEquals(-807, SumSquares1.sumSquares(Arrays.asList(-1, -2, -3, -4, -5, -6, -7, -8, -9)));
    }
    @Test
    public void testNegativeNumbersList3() {
        assertEquals(-807, SumSquares1.sumSquares(Arrays.asList(-1, -2, -3, -4, -5, -6, -7, -8, -9)));
    }
    @Test
    public void testNegativeNumbersListCorrected3() {
        assertEquals(-807, SumSquares1.sumSquares(Arrays.asList(-1, -2, -3, -4, -5, -6, -7, -8, -9)));
    }
    @Test
    public void testSumSquares_EmptyList_New() {
        List<Object> lst = new ArrayList<>();
        int expected_result = 0;
        int result = SumSquares1.sumSquares(lst);
        assertEquals(expected_result, result);
    }
    @Test
    public void testSumSquares_SingleElementList2() {
        List<Object> lst = Arrays.asList(1);
        int expected_result = 1;
        int result = SumSquares1.sumSquares(lst);
        assertEquals(expected_result, result);
    }
    @Test
    public void testSumSquares_MultipleElementsList_Duplicate() {
        List<Object> lst = Arrays.asList(1, 2, 3);
        int expected_result = 6;
        int result = SumSquares1.sumSquares(lst);
        assertEquals(expected_result, result);
    }
    @Test
    public void testSumSquares_NullInput_2() {
        List<Object> lst = null;
        assertThrows(NullPointerException.class, () -> SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares_NonIntegerInput() {
        List<Object> lst = Arrays.asList("a", 1, 2);
        assertThrows(ClassCastException.class, () -> SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares_EmptyList_Duplicate() {
        List<Object> lst = new ArrayList<>();
        int result = SumSquares1.sumSquares(lst);
        assertEquals(0, result);
    }
    @Test
    public void testSumSquares_NullInput_New_1() {
        assertThrows(NullPointerException.class, () -> SumSquares1.sumSquares(null));
    }
    @Test
    public void testSumSquares_SquareAndCubeOperations_Fixed() {
        List<Object> lst = Arrays.asList(1, 2, 3, 4);
        int result = SumSquares1.sumSquares(lst);
        assertEquals(22, result);
    }
    
    @Test
     void testNothing(){
         SumSquares1 s = new SumSquares1();
         }
    @Test
    public void testSumOfSquaresWithEmptyListUnique() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList();
        assertEquals(0, SumSquares1.sumSquares(input));
    }
                                  

}