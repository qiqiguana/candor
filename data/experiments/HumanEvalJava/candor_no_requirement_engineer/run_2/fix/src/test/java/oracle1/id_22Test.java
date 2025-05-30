package oracle1;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FilterIntegers.
*/
class FilterIntegersTest {
    @Test
    void filterIntegers_EmptyList_ReturnsEmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, FilterIntegers.filterIntegers(input));
    }
    
    @Test
        void testNothing(){
            FilterIntegers s = new FilterIntegers();
            }
    @Test
    public void testFilterIntegers_EmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, FilterIntegers.filterIntegers(input));
    }
    @Test
    public void testFilterIntegers_SingleInteger() {
        List<Object> input = Arrays.asList(5);
        List<Object> expected = Arrays.asList(5);
        assertEquals(expected, FilterIntegers.filterIntegers(input));
    }
                                    
}