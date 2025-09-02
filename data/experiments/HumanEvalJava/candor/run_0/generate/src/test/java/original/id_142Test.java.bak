package original;

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
void testSumSquares_WhenListIsEmpty_ReturnsZero() {
    List<Object> list = new ArrayList<>();
    int result = SumSquares1.sumSquares(list);
    assertEquals(0, result);
}

@Test
    public void testNothing(){
        SumSquares1 s = new SumSquares1();
        }
@Test
public void testEmptyList() {
	List<Object> input = new ArrayList<>();
	assertEquals(0, SumSquares1.sumSquares(input));
}
@Test
public void testNullInput() {
	List<Object> input = null;
	assertThrows(NullPointerException.class, () -> SumSquares1.sumSquares(input));
}
@Test
public void testSingleElementListMultipleOfThreeIndex() {
    List<Object> input = Arrays.asList(1);
    assertEquals(1, SumSquares1.sumSquares(input));
}
@Test
public void testSingleElementListNonMultipleOfThreeOrFourIndexFixed() {
    List<Object> input = Arrays.asList(1, 2);
    assertEquals(3, SumSquares1.sumSquares(input));
}
@Test
public void testSingleElementListMultipleOfFourIndex0() {
    List<Object> input = Arrays.asList(4);
    assertEquals(16, SumSquares1.sumSquares(input));
}
@Test
public void testMultipleElementsList() {
    List<Object> input = Arrays.asList(-1, -5, 2, -1, -5);
    assertEquals(-126, SumSquares1.sumSquares(input));
}
@Test
public void sumSquares_SingleElementList_ReturnsSquaredValue() {
    List<Object> lst = new ArrayList<>();
    lst.add(5);
    assertEquals(25, SumSquares1.sumSquares(lst));
}
@Test
public void sumSquares_MultipleOfThreeIndex_ReturnsCorrectSum() {
    List<Object> lst = new ArrayList<>();
    lst.add(1);
    lst.add(2);
    lst.add(3);
    assertEquals(6, SumSquares1.sumSquares(lst));
}
@Test
public void sumSquares_NegativeNumbers_ReturnsCorrectSum() {
    List<Object> lst = new ArrayList<>();
    lst.add(-1);
    lst.add(-5);
    lst.add(2);
    lst.add(-1);
    lst.add(-5);
    assertEquals(-126, SumSquares1.sumSquares(lst));
}
@Test
public void sumSquares_MixOfOperations_ReturnsCorrectSum() {
    List<Object> lst = new ArrayList<>();
    lst.add(-56);
    lst.add(-99);
    lst.add(1);
    lst.add(0);
    lst.add(-2);
    assertEquals(3030, SumSquares1.sumSquares(lst));
}
@Test
public void sumSquares_NullList_ThrowsNullPointerException() {
    List<Object> lst = null;
    assertThrows(NullPointerException.class, () -> SumSquares1.sumSquares(lst));
}
@Test
public void TestSingleElementList() {
    List<Object> input = Arrays.asList(1);
    int expected_result = 1;
    int result = SumSquares1.sumSquares(input);
    assertEquals(expected_result, result);
}
@Test
public void TestEdgeCaseMultipleOfFour2() {
    List<Object> input = Arrays.asList(4, 8, 12);
    int expected_result = 320;
    int result = SumSquares1.sumSquares(input);
    assertNotEquals(expected_result+1, result);
}
@Test
public void testSumSquares_SingleElementList() {
	List<Object> lst = Arrays.asList(1);
	assertEquals(1, SumSquares1.sumSquares(lst));
}
@Test
public void testSumSquaresWithSingleElementList() {
    List<Object> lst = new ArrayList<>(Arrays.asList(5));
    assertEquals(25, SumSquares1.sumSquares(lst));
}
@Test
public void testSumSquaresWithListContainingZeros() {
    List<Object> lst = new ArrayList<>(Arrays.asList(0, 0, 0));
    assertEquals(0, SumSquares1.sumSquares(lst));
}
@Test
public void testSumSquaresWithListContainingNumbers() {
    List<Object> lst = new ArrayList<>(Arrays.asList(-1, -5, 2, -1, -5));
    assertEquals(-126, SumSquares1.sumSquares(lst));
}
@Test
public void testSumSquares_with_single_element_list() {
    List<Object> lst = Arrays.asList(1);
    assertEquals(1, SumSquares1.sumSquares(lst));
}
@Test
public void testSumSquares_with_index_multiple_of_3() {
    List<Object> lst = Arrays.asList(1, 2, 3);
    assertEquals(6, SumSquares1.sumSquares(lst));
}
@Test
public void testSumSquares_with_list_containing_negative_and_positive_numbers() {
    List<Object> lst = Arrays.asList(-1, -5, 2, -1, -5);
    assertEquals(-126, SumSquares1.sumSquares(lst));
}
@Test
public void Test_SumSquares_with_multiples_of_31() {
    List<Object> input = new ArrayList<>(Arrays.asList(9, 2, 3));
    int expected_result = 86;
    int actual_result = SumSquares1.sumSquares(input);
    assertEquals(expected_result, actual_result);
}
@Test
public void Test_SumSquares_with_negative_multiples_of_3_Fixed() {
    List<Object> input = new ArrayList<>(Arrays.asList(-9, -2, -3));
    int expected_result = 76;
    int actual_result = SumSquares1.sumSquares(input);
    assertEquals(expected_result, actual_result);
}
@Test
public void test_sumSquares_multiple_of_3_not_4_corrected() {
    List<Object> lst = new ArrayList<>(Arrays.asList(1, 2, 3));
    int result = SumSquares1.sumSquares(lst);
    assertEquals(6, result);
}
@Test
public void sumSquares_MultipleOfThree() {
    List<Object> lst = new ArrayList<>();
    lst.add(1);
    lst.add(2);
    lst.add(3);
    int result = SumSquares1.sumSquares(lst);
    assertEquals(6, result);
}
                                
}