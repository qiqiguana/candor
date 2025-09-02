package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StrongestExtension.
*/
class StrongestExtensionTest {
    @Test
    void strongestExtension_WhenCalledWithValidInput_ReturnsExpectedResult() {
        String className = "my_class";
        List<String> extensions = List.of("AA", "Be", "CC");
        String expected = "my_class.AA";
        assertEquals(expected, StrongestExtension.strongestExtension(className, extensions));
    }
}