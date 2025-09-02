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
    assertEquals(2, result.size());
    assertEquals("Saturn", result.get(0));
    assertEquals("Uranus", result.get(1));
}

}