package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
void testByLength_EmptyArray_ReturnsEmptyArray() {
// Arrange
List<Object> input = new ArrayList<>();
List<Object> expectedOutput = new ArrayList<>();

// Act
List<Object> actualOutput = ByLength.byLength(input);

// Assert
assertIterableEquals(expectedOutput, actualOutput);
}

@Test
    public void testNothing(){
        ByLength s = new ByLength();
        }
@Test
public void testInstanceOfCheck() {
    List<Object> input = new ArrayList<>();
    input.add(new Object());
    List<Object> result = ByLength.byLength(input);
    assertEquals(Collections.emptyList(), result);
}
@Test
public void testPrimitiveTypeCheck() {
    List<Object> input = new ArrayList<>();
    input.add(10);
    List<Object> result = ByLength.byLength(input);
    assertEquals(Collections.emptyList(), result);
}
@Test
public void test_byLength_DescendingOrder() {
    List<Object> input = new ArrayList<>();
    input.add(3);
    input.add(2);
    input.add(1);
    List<Object> result = ByLength.byLength(input);
    assertEquals(Arrays.asList("Three", "Two", "One"), result);
}
@Test
public void testSwitchExpressionDefaultCase() {
    List<Object> input = new ArrayList<>();
    input.add(4);
    List<Object> result = ByLength.byLength(input);
    assertEquals(Collections.singletonList("Four"), result);
}
@Test
public void testByLength_withArrayContainingFive() {
	List<Object> arr = new ArrayList<>();
	arr.add(9);
	arr.add(4);
	arr.add(8);
	List<Object> expected_result = new ArrayList<>();
	expected_result.add("Nine");
	expected_result.add("Eight");
	expected_result.add("Four");
	assertEquals(expected_result, ByLength.byLength(arr));
}
@Test
public void testByLength_withArrayContainingSix() {
	List<Object> arr = new ArrayList<>();
	arr.add(9);
	arr.add(4);
	arr.add(8);
	arr.add(6);
	List<Object> expected_result = new ArrayList<>();
	expected_result.add("Nine");
	expected_result.add("Eight");
	expected_result.add("Six");
	expected_result.add("Four");
	assertEquals(expected_result, ByLength.byLength(arr));
}
@Test
public void testByLength_withArrayContainingSeven() {
	List<Object> arr = new ArrayList<>();
	arr.add(9);
	arr.add(4);
	arr.add(8);
	arr.add(7);
	List<Object> expected_result = new ArrayList<>();
	expected_result.add("Nine");
	expected_result.add("Eight");
	expected_result.add("Seven");
	expected_result.add("Four");
	assertEquals(expected_result, ByLength.byLength(arr));
}
@Test
public void testByLength_with_numbers_corresponding_to_Five_and_Six() {
	List<Object> input = new ArrayList<>();
	input.add(5);
	input.add(6);
	List<Object> expected = new ArrayList<>();
	expected.add("Six");
	expected.add("Five");
	assertEquals(expected, ByLength.byLength(input));
}
@Test
public void testByLength_with_numbers_outside_range_ignored() {
    List<Object> input = new ArrayList<>();
    for (int i = 1; i <= 9; i++) {
        input.add(i);
    }
    input.add(10);
    input.add(-5);
    List<Object> result = ByLength.byLength(input);
    assertEquals(9, result.size());
    String[] expectedNames = {"Nine", "Eight", "Seven", "Six", "Five", "Four", "Three", "Two", "One"};
    for (int i = 0; i < expectedNames.length; i++) {
        assertEquals(expectedNames[i], result.get(i));
    }
}
                                
}