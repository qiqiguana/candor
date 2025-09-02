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
    void testpluck_ReturnsEmptyList_WhenInputIsEmpty() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Pluck.pluck(input));
    }
}
