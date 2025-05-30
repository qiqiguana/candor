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
    void testFilterIntegers_withListContainingOnlyIntegers_ReturnsSameList() {
        // Arrange
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);

        // Act
        List<Object> result = FilterIntegers.filterIntegers(values);

        // Assert
        assertEquals(values, result);
    }
    
    @Test
        public void testNothing(){
            FilterIntegers s = new FilterIntegers();
            }
    @Test
    public void testFilterIntegersClassInstantiation() {
        // Instantiate the FilterIntegers class
        FilterIntegers filterIntegers = new FilterIntegers();
        // Assert that the instantiation was successful
        assertNotNull(filterIntegers);
    }
    @Test
    public void testFilterIntegersWithMixedTypes() {
        List<Object> input = new ArrayList<>();
        input.add("a");
        input.add(3.14);
        input.add(5);
        List<Object> expected = new ArrayList<>();
        expected.add(5);
        assertEquals(expected, FilterIntegers.filterIntegers(input));
    }
                                    
}