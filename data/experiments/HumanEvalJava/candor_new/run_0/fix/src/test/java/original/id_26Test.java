package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveDuplicates.
*/
class RemoveDuplicatesTest {
    @Test
    void removeDuplicates_removesOnlyDuplicates() {
        List<Object> numbers = new ArrayList<>(List.of(1, 2, 3, 2, 4));
        List<Object> expected = new ArrayList<>(List.of(1, 3, 4));
        assertEquals(expected, RemoveDuplicates.removeDuplicates(numbers));
    }
    
    @Test
        public void testNothing(){
            RemoveDuplicates s = new RemoveDuplicates();
            }
                                    
}