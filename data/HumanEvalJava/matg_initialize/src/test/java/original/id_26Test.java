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
    void testRemoveDuplicates_singleElementList() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }
}