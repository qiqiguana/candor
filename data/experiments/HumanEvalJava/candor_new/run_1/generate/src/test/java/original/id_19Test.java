package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortNumbers.
*/
class SortNumbersTest {
    @Test
    void testSortNumbers_SimpleInput_ReturnsSortedString() {
        String input = "three one five";
        String expectedOutput = "one three five";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
    
    @Test
        public void testNothing(){
            SortNumbers s = new SortNumbers();
            }
    @Test
    public void testSortNumbers_two() {
        String result = SortNumbers.sortNumbers("two");
        assertEquals("two", result);
    }
    @Test
    public void testSortNumbers_four() {
        String result = SortNumbers.sortNumbers("four");
        assertEquals("four", result);
    }
    @Test
    public void testSortNumbers_five_six() {
        String result = SortNumbers.sortNumbers("five six");
        assertEquals("five six", result);
    }
    @Test
    public void testSortNumbers_seven_eight_nine() {
        String result = SortNumbers.sortNumbers("seven eight nine");
        assertEquals("seven eight nine", result);
    }
    @Test
    public void testSortNumbers_no_input() {
        String result = SortNumbers.sortNumbers("");
        assertEquals("", result);
    }
    @Test
    public void test_sort_numbers_one_zero() {
        String result = SortNumbers.sortNumbers("one zero");
        assertEquals("zero one", result.trim());
    }
                                    
}