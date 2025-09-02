package original;

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
    void testFilterIntegersWithMixedValues() {
        List<Object> values = new ArrayList<>();
        values.add("a");
        values.add(3.14);
        values.add(5);
        List<Object> expected = new ArrayList<>();
        expected.add(5);

        assertEquals(expected, FilterIntegers.filterIntegers(values));
    }
    
    @Test
        public void testNothing(){
            FilterIntegers s = new FilterIntegers();
            }
    @Test
    public void testFilterIntegersWithNullInput() {
        List<Object> values = null;
        assertThrows(NullPointerException.class, () -> FilterIntegers.filterIntegers(values));
    }
    @Test
    public void testFilterIntegersWithOnlyIntegers() {
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
        List<Object> result = FilterIntegers.filterIntegers(values);
        assertEquals(result, Arrays.asList(1, 2, 3));
    }
                                    
}