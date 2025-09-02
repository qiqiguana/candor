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
    void removeDuplicates_should_ReturnEmptyList_WhenInputIsEmpty() {
        List<Object> numbers = new ArrayList<>();
        List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
        assertTrue(result.isEmpty());
    }
}