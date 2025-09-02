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
    void testFilterIntegersWithMultipleValues() {
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("abc");
        values.add(3.14);
        values.add(5);
        List<Object> result = FilterIntegers.filterIntegers(values);
        assertEquals(result.size(), 2);
    }
}