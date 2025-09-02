package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FilterIntegers.
*/
class FilterIntegersTest {
    @Test
    void testFilterIntegers_onlyIntegerValuesPassed() {
        // Given
        List<Object> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);

        // When
        List<Object> result = FilterIntegers.filterIntegers(input);

        // Then
        assertEquals(3, result.size());
    }
    
    @Test
        public void testNothing(){
            FilterIntegers s = new FilterIntegers();
            }
    @Test
    public void testFilterIntegersInitialization() {
        FilterIntegers filterIntegers = new FilterIntegers();
        assertNotNull(filterIntegers);
    }
    @Test
    public void testFilterIntegersWithEmptyList() {
        List<Object> values = new ArrayList<>();
        List<Object> result = FilterIntegers.filterIntegers(values);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testFilterIntegersWithNonIntegerValues() {
        List<Object> values = new ArrayList<>();
        values.add("a");
        values.add(3.14);
        values.add("b");
        List<Object> result = FilterIntegers.filterIntegers(values);
        assertTrue(result.isEmpty());
    }
                                    
}