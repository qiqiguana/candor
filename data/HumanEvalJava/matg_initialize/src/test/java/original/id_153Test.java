package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StrongestExtension.
*/
class StrongestExtensionTest {
    @Test
    void testStrongestExtension_SimpleCase_ReturnsExpectedResult() {
        List<String> extensions = List.of("AA", "Be", "CC");
        String actual = StrongestExtension.strongestExtension("my_class", extensions);
        assertEquals("my_class.AA", actual);
    }
}