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
    public void testBf_01() {
        List<Object> result = Bf.bf("Jupiter", "Neptune");
        assertEquals(2, result.size());
        assertTrue(result.contains("Saturn"));
        assertTrue(result.contains("Uranus"));
    }
}
