package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Bf.
*/
class BfTest {

    @Test
    void testBf() {
        List<Object> result = Bf.bf("Jupiter", "Neptune");
        List<Object> expected = new ArrayList<>();
        expected.add("Saturn");
        expected.add("Uranus");
        assertEquals(expected, result);
    }

}