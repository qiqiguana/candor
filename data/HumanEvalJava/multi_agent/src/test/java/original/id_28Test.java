package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Concatenate.
*/
class ConcatenateTest {
    @Test
    void testConcatenate_emptyList() {
        List<Object> input = new ArrayList<>();
        String expected = "";
        String actual = Concatenate.concatenate(input);
        assertEquals(expected, actual);
    }
}