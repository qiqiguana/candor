package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of ByLength.
*/
class ByLengthTest {

    @Test
    void testByLength_EmptyArray_ReturnsEmptyArray() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, ByLength.byLength(input));
    }
}