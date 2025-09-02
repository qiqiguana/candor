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
        String expected = "zero four five seven eight nine";
        assertEquals(expected, SortNumbers.sortNumbers(numbers));
    }
    
    @Test
        public void testNothing(){
            SortNumbers s = new SortNumbers();
            }
    @Test
    public void testSortNumbersWithOneNumber() {
        String input = "three";
        String expectedResult = "three";
        assertEquals(expectedResult, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testSortNumbersWithMultipleNumbers() {
        String input = "five zero four seven nine eight";
        String expectedResult = "zero four five seven eight nine";
        assertEquals(expectedResult, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testSortNumbersWithEmptyString() {
        String input = "";
        String expectedResult = "";
        assertEquals(expectedResult, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testSortNumbersWithInvalidInput() {
        String input = "ten";
        String expectedResult = "";
        assertEquals(expectedResult, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testSortNumbersWithDuplicateNumbers() {
        String input = "three three five";
        String expectedResult = "three three five";
        assertEquals(expectedResult, SortNumbers.sortNumbers(input));
    }
    @Test
    public void testSortNumbersWithNumbersInReverseOrder() {
        String input = "nine eight seven six five four three two one";
        String expectedResult = "one two three four five six seven eight nine";
        assertEquals(expectedResult, SortNumbers.sortNumbers(input));
    }
                                    
}