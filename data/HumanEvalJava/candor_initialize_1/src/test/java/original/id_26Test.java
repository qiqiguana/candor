package original;

import java.util.Arrays; import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveDuplicates.
*/
class RemoveDuplicatesTest {
    @Test
    void testRemoveDuplicates_SingleElement() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
        assertEquals(1, result.size());
    }
    
    @Test
        public void testNothing(){
            RemoveDuplicates s = new RemoveDuplicates();
            }
    @Test
    public void testRemoveDuplicatesOnEmptyListFixed() {
        List<Object> input = new ArrayList<>();
        assertTrue(RemoveDuplicates.removeDuplicates(input).isEmpty());
    }
    @Test public void testRemoveDuplicatesOnListWithNoDuplicates() { List<java.lang.Object> input = Arrays.asList(1, 2, 3, 2, 4); List<java.lang.Object> expected = Arrays.asList(1, 3, 4); assertEquals(expected, RemoveDuplicates.removeDuplicates(input));}
                                    
}