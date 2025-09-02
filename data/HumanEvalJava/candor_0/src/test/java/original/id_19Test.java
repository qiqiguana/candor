package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortNumbers.
*/
class SortNumbersTest {
    @Test
    void testSortNumbers_SortedString_ReturnsSortedString() {
        String numbers = "five zero four seven nine eight";
        String expected = "zero four five seven eight nine";
        assertEquals(expected, SortNumbers.sortNumbers(numbers));
    }
    
    @Test
        public void testNothing(){
            SortNumbers s = new SortNumbers();
            }
    @Test
    public void testEmptyStringInput() {
        String input = "";
        String expectedOutput = "";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testSingleNumberInput() {
        String input = "three";
        String expectedOutput = "three";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testMultipleNumbersInput() {
        String input = "three five one";
        String expectedOutput = "one three five";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testInvalidInputTen() {
        String input = "ten";
        String expectedOutput = "";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testNullInputFixed() {
        String input = null;
        assertThrows(NullPointerException.class, () -> SortNumbers.sortNumbers(input));
    }
    @Test
    public void test_sortNumbers_emptyString() {
        String result = SortNumbers.sortNumbers("");
        assertEquals("", result);
    }
    @Test
    public void test_sortNumbers_invalidInput() {
        String result = SortNumbers.sortNumbers("ten");
        assertEquals("", result);
    }
    @Test
    public void test_sortNumbers_singleNumber() {
        String result = SortNumbers.sortNumbers("five");
        assertEquals("five", result);
    }
    @Test
    public void test_sortNumbers_multipleNumbers() {
        String result = SortNumbers.sortNumbers("nine one eight");
        assertEquals("one eight nine", result);
    }
    @Test
    public void testSingleDigitNumbersReverse() {
        String input = "nine eight seven six five four three two one";
        String expectedOutput = "one two three four five six seven eight nine";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testSingleDigitNumbersAscending() {
        String input = "one two three four five six seven eight nine";
        String expectedOutput = "one two three four five six seven eight nine";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
                                    
}