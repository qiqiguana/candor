package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Pluck.
*/
class PluckTest {
    @Test
    void testpluck_SmallestEvenValueAndIndex() {
        List<Object> input = new ArrayList<>();
        input.add(4);
        input.add(2);
        input.add(3);
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(1);
        assertEquals(expected, Pluck.pluck(input));
    }
}