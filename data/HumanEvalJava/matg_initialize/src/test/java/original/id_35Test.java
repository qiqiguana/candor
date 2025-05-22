package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MaxElement.
*/
class MaxElementTest {
    @Test
    void testMaxElement_ReturnsMaximumValueInList() {
        List<Integer> list = List.of(5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10);
        int expectedMax = 123;
        assertEquals(expectedMax, MaxElement.maxElement(list));
    }
}