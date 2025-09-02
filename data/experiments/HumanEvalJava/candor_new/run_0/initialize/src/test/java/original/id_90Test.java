package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of NextSmallest.
*/
class NextSmallestTest {
    @Test
    void test_next_smallest_with_unique_elements() {
        List<Object> numbers = List.of(1, 2, 3, 4, 5);
        Integer result = NextSmallest.nextSmallest(numbers);
        assertEquals(Integer.valueOf(2), result);
    }
}
