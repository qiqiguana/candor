package original;

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
void testByLength_SingleElementArray_ReturnsExpectedResult() {
    List<Object> arr = new ArrayList<>();
    arr.add(5);
    List<Object> result = ByLength.byLength(arr);
    assertEquals("Five", result.get(0));
}

@Test
    public void testNothing(){
        ByLength s = new ByLength();
        }
@Test
public void testByLength_with_single_element() {
    List<Object> arr = new ArrayList<>();
    arr.add(1);
    List<Object> result = ByLength.byLength(arr);
    assertEquals("One", result.get(0));
}
@Test
public void testByLength_with_non_integer() {
    List<Object> arr = new ArrayList<>();
    arr.add(1);
    arr.add("a");
    List<Object> result = ByLength.byLength(arr);
    assertEquals(1, result.size());
    assertEquals("One", result.get(0));
}
@Test
public void testByLength_with_out_of_range_integer() {
    List<Object> arr = new ArrayList<>();
    arr.add(10);
    List<Object> result = ByLength.byLength(arr);
    assertTrue(result.isEmpty());
}
@Test
public void testByLength_with_negative_integer() {
    List<Object> arr = new ArrayList<>();
    arr.add(-1);
    List<Object> result = ByLength.byLength(arr);
    assertTrue(result.isEmpty());
}
@Test
public void testByLength_with_multiple_elements() {
    List<Object> arr = new ArrayList<>();
    arr.add(2);
    arr.add("a");
    arr.add(10);
    List<Object> result = ByLength.byLength(arr);
    assertEquals(1, result.size());
    assertEquals("Two", result.get(0));
}
@Test
public void testByLength_SingleDigitNumber_3() {
    List<Object> input = new ArrayList<>();
    input.add(new Integer(3));
    List<Object> expected = new ArrayList<>();
    expected.add("Three");
    assertEquals(expected, ByLength.byLength(input));
}
@Test
public void testByLength_SingleDigitNumber_4() {
    List<Object> input = new ArrayList<>();
    input.add(new Integer(4));
    List<Object> expected = new ArrayList<>();
    expected.add("Four");
    assertEquals(expected, ByLength.byLength(input));
}
@Test
public void testByLength_SingleDigitNumber_8() {
    List<Object> input = new ArrayList<>();
    input.add(new Integer(8));
    List<Object> expected = new ArrayList<>();
    expected.add("Eight");
    assertEquals(expected, ByLength.byLength(input));
}
@Test
public void testByLength_SingleDigitNumber_9() {
    List<Object> input = new ArrayList<>();
    input.add(new Integer(9));
    List<Object> expected = new ArrayList<>();
    expected.add("Nine");
    assertEquals(expected, ByLength.byLength(input));
}
@Test
public void Test_byLength_with_input_containing_six() {
    List<Object> arr = new ArrayList<>();
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(4);
    arr.add(5);
    arr.add(6);
    List<Object> expected = new ArrayList<>();
    expected.add("Six");
    expected.add("Five");
    expected.add("Four");
    expected.add("Three");
    expected.add("Two");
    expected.add("One");
    assertEquals(expected, ByLength.byLength(arr));
}
@Test
public void Test_byLength_with_input_containing_seven() {
    List<Object> arr = new ArrayList<>();
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(4);
    arr.add(5);
    arr.add(6);
    arr.add(7);
    List<Object> expected = new ArrayList<>();
    expected.add("Seven");
    expected.add("Six");
    expected.add("Five");
    expected.add("Four");
    expected.add("Three");
    expected.add("Two");
    expected.add("One");
    assertEquals(expected, ByLength.byLength(arr));
}
                                
}