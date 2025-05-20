package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Specialfilter.
*/
class SpecialfilterTest {
    @Test
    void specialFilter_shouldReturnZero_WhenInputIsEmpty() {
        List<Object> input = new ArrayList<>();
        assertEquals(0, Specialfilter.specialfilter(input));
    }
    @Test
    public void test_specialfilter_EmptyList() {
        List<Object> nums = new ArrayList<>();
        assertEquals(0, Specialfilter.specialfilter(nums));
    }
    @Test
    public void test_specialfilter_SingleElement() {
        List<Object> nums = new ArrayList<>();
        nums.add(5);
        assertEquals(0, Specialfilter.specialfilter(nums));
    }
    @Test
    public void test_specialfilter_MultipleElements() {
        List<Object> nums = new ArrayList<>();
        nums.add(15);
        nums.add(-73);
        nums.add(14);
        nums.add(-15);
        assertEquals(1, Specialfilter.specialfilter(nums));
    }
    @Test
    public void test_specialfilter_OddDigits() {
        List<Object> nums = new ArrayList<>();
        nums.add(33);
        nums.add(-2);
        nums.add(-3);
        nums.add(45);
        nums.add(21);
        nums.add(109);
        assertEquals(2, Specialfilter.specialfilter(nums));
    }
    @Test
    public void test_specialfilter_NoOddDigits() {
        List<Object> nums = new ArrayList<>();
        nums.add(42);
        nums.add(-20);
        nums.add(-30);
        assertEquals(0, Specialfilter.specialfilter(nums));
    }
    @Test
    public void test_specialfilter_NullList() {
        List<Object> nums = null;
        assertThrows(NullPointerException.class, () -> Specialfilter.specialfilter(nums));
    }
    @Test
    public void test_specialfilter_MixedNumbers() {
    	List<Object> input = Arrays.asList(11, -23, 35);
    	int expected_result = 2;
    	assertEquals(expected_result, Specialfilter.specialfilter(input));
    }
    public class TestSpecialFilter { @Test void testInstantiation() { Specialfilter specialfilter = new Specialfilter(); assertNotNull(specialfilter); }}
                                     @Test void testFirstDigitOdd() { List<Object> nums = Arrays.asList(13); assertEquals(1, Specialfilter.specialfilter(nums)); }
                                     @Test void testLastDigitOdd() { List<Object> nums = Arrays.asList(31); assertEquals(1, Specialfilter.specialfilter(nums)); }
                                     @Test void testFirstAndLastDigitsOdd() { List<Object> nums = Arrays.asList(33); assertEquals(1, Specialfilter.specialfilter(nums)); }
                                     @Test void testNoFirstDigitOdd() { List<Object> nums = Arrays.asList(42); assertEquals(0, Specialfilter.specialfilter(nums)); }
                                     @Test void testNoLastDigitOdd() { List<Object> nums = Arrays.asList(24); assertEquals(0, Specialfilter.specialfilter(nums)); }
                                     @Test
                                     public void test_specialfilter_mixed_numbers() {
                                         List<Object> nums = Arrays.asList(11, -23, 35);
                                         assertEquals(2, Specialfilter.specialfilter(nums));
                                     }
                                     @Test
                                     public void test_specialfilter_first_digit_odd() {
                                         List<Object> nums = Arrays.asList(13);
                                         assertEquals(1, Specialfilter.specialfilter(nums));
                                     }
                                     @Test
                                     public void test_specialfilter_last_digit_odd() {
                                         List<Object> nums = Arrays.asList(31);
                                         assertEquals(1, Specialfilter.specialfilter(nums));
                                     }
                                     @Test
                                     public void test_specialFilter_emptyList() {
                                         List<Object> nums = new ArrayList<>();
                                         int result = Specialfilter.specialfilter(nums);
                                         assertEquals(0, result);
                                     }
                                     @Test
                                     public void test_specialFilter_singleElement() {
                                         List<Object> nums = Arrays.asList(5);
                                         int result = Specialfilter.specialfilter(nums);
                                         assertEquals(0, result);
                                     }
                                     @Test
                                     public void test_specialFilter_multipleElements() {
                                         List<Object> nums = Arrays.asList(15, -73, 14, -15);
                                         int result = Specialfilter.specialfilter(nums);
                                         assertEquals(1, result);
                                     }
                                     @Test
                                     public void test_specialFilter_firstDigitEven() {
                                         List<Object> nums = Arrays.asList(24, 42, 68);
                                         int result = Specialfilter.specialfilter(nums);
                                         assertEquals(0, result);
                                     }
                                     @Test
                                     void testEmptyList() {
                                         List<Object> input = new ArrayList<>();
                                         assertEquals(0, Specialfilter.specialfilter(input));
                                     }
                                     @Test
                                     void testSingleElement() {
                                         List<Object> input = Arrays.asList(1);
                                         assertEquals(0, Specialfilter.specialfilter(input));
                                     }
                                     @Test
                                     void testMultipleElements() {
                                         List<Object> input = Arrays.asList(15, -73, 14, -15);
                                         assertEquals(1, Specialfilter.specialfilter(input));
                                     }
                                     @Test
                                     void testAllGreaterThan10() {
                                         List<Object> input = Arrays.asList(15, 73, 14, 115);
                                         assertEquals(3, Specialfilter.specialfilter(input));
                                     }
                                     @Test
                                     void testNoGreaterThan10() {
                                         List<Object> input = Arrays.asList(1, 2, 3, 4);
                                         assertEquals(0, Specialfilter.specialfilter(input));
                                     }
                                     @Test
                                     void testNullInput() {
                                         List<Object> input = null;
                                         assertThrows(NullPointerException.class, () -> Specialfilter.specialfilter(input));
                                     }
}