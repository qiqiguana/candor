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
void testFilterIntegersShouldReturnEmptyListWhenInputIsEmpty() {
    List<Object> input = new ArrayList<>();
    List<Object> expected = new ArrayList<>();
    assertEquals(expected, FilterIntegers.filterIntegers(input));
}

@Test
    public void testNothing(){
        FilterIntegers s = new FilterIntegers();
        }
@Test
public void testFilterIntegersWithMixedDataTypes() {
    List<Object> input = new ArrayList<>();
    input.add("a");
    input.add(3.14);
    input.add(5);
    List<Object> expectedOutput = new ArrayList<>();
    expectedOutput.add(5);
    assertEquals(expectedOutput, FilterIntegers.filterIntegers(input));
}
                                
}