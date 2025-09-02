package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortNumbers.
*/
class SortNumbersTest {
    @Test
    void testSortNumbers() {
        String numbers = "five zero four seven nine eight";
        String expectedSortedNumbers = "zero four five seven eight nine";
        assertEquals(expectedSortedNumbers, SortNumbers.sortNumbers(numbers));
    }
    
    @Test
        public void testNothing(){
            SortNumbers s = new SortNumbers();
            }
    @Test
    public void testSortNumbers_OneValidNumber() {
    	String result = SortNumbers.sortNumbers("one");
    	assertEquals("one", result);
    }
    @Test
    public void testSortNumbers_MultipleValidNumbers() {
    	String result = SortNumbers.sortNumbers("three one five");
    	assertEquals("one three five", result);
    }
    @Test
    public void testSortNumbers_OneInvalidInput() {
    	String result = SortNumbers.sortNumbers("ten");
    	assertEquals("", result);
    }
    @Test
    public void testSortNumberWithTwo() {
        String[] numbers = {"three", "one", "two"};
        String expected_result = "one two three";
        assertEquals(expected_result, SortNumbers.sortNumbers(String.join(" ", numbers)));
    }
    @Test
    public void testSortNumberWithSix() {
        String[] numbers = {"five", "zero", "six"};
        String expected_result = "five six zero";
        assertNotEquals(expected_result, SortNumbers.sortNumbers(String.join(" ", numbers)));
    }
                                    
}