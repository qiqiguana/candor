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
    void testConcatenate() {
        List<Object> strings = new ArrayList<>();
        strings.add("Hello");
        strings.add("World");
        String result = Concatenate.concatenate(strings);
        assertEquals("", result);
    }
}