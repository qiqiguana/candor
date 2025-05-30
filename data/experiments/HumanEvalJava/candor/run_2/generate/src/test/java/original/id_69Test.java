package original;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Search.
*/
class SearchTest {
    @Test
    void testSearch() {
        List<Integer> lst = new ArrayList<>();
        lst.add(5);
        lst.add(7);
        lst.add(7);
        lst.add(8);
        lst.add(9);
        assertEquals(Search.search(lst), -1);
    }
    
    @Test
        public void testNothing(){
            Search s = new Search();
            }
    @Test
    public void test_multiple_occurrences_of_number_greater_than_its_frequency1() {
        List<Integer> numbers = Arrays.asList(5, 5, 2);
        int result = Search.search(numbers);
        assertEquals(-1, result);
    }
    @Test
    public void test_multiple_occurrences_of_number_greater_than_its_frequency2() {
        List<Integer> numbers = Arrays.asList(5, 5, 2);
        int result = Search.search(numbers);
        assertEquals(-1, result);
    }
    @Test
    public void testEmptyList() {
        List<Integer> input = new ArrayList<>();
        assertEquals(-1, Search.search(input));
    }
    @Test
    public void multipleElementsListFrequenciesNotAllEqualValues_3() {
        List<Integer> input = Arrays.asList(2, 4, 5);
        assertEquals(-1, Search.search(input));
    }
    @Test
    public void test_search_method_with_single_element_list() {
        List<Integer> lst = Arrays.asList(5);
        int result = Search.search(lst);
        assertEquals(-1, result);
    }
    @Test
    public void test_search_method_with_all_elements_same() {
        List<Integer> lst = Arrays.asList(7, 7, 7, 7);
        int result = Search.search(lst);
        assertEquals(-1, result);
    }
    @Test
    public void test_search_method_with_duplicate_elements() {
        List<Integer> lst = Arrays.asList(2, 4, 6, 8, 10);
        int result = Search.search(lst);
        assertEquals(-1, result);
    }
    @Test
    public void testSingleElementListInputWithFrequencyLessThanTheNumberItself() {
        // Arrange
        List<Integer> numbers = Arrays.asList(5);
        // Act and Assert
        assertEquals(-1, Search.search(numbers));
    }
    @Test
    public void testMultipleElementsListInputWithNoFrequencyMeetingTheCondition() {
        // Arrange
        List<Integer> numbers = Arrays.asList(2, 3, 4);
        // Act and Assert
        assertEquals(-1, Search.search(numbers));
    }
    @Test
    public void testMultipleElementsListInputWithOneFrequencyMeetingTheCondition2() {
        // Arrange
        List<Integer> numbers = Arrays.asList(3, 3, 3);
        // Act and Assert
        assertEquals(3, Search.search(numbers));
    }
                                    
}