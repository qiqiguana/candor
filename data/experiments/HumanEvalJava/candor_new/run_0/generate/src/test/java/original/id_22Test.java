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
    void testFilterIntegersWithMixedValues() {
        List<Object> values = new ArrayList<>();
        values.add("a");
        values.add(3.14);
        values.add(5);
        values.add("b");
        values.add(new Integer(7));

        List<Object> result = FilterIntegers.filterIntegers(values);
        assertEquals(2, result.size());
    }
    
    @Test
        public void testNothing(){
            FilterIntegers s = new FilterIntegers();
            }
                                    
}