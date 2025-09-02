package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
* Test class of DoubleTheDifference.
*/
class DoubleTheDifferenceTest {
    @Test
    void testDoubleTheDifference() {
        List<Object> lst = List.of(1, 3, 2, 0);
        assertEquals(10, DoubleTheDifference.doubleTheDifference(lst));
    }
}