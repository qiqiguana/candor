package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StrongestExtension.
*/
class StrongestExtensionTest {

    @Test
    void testStrongestExtension() {
        String className = "Slices";
        List<String> extensions = List.of("SErviNGSliCes", "Cheese", "StuFfed");
        String expected = "Slices.SErviNGSliCes";
        assertEquals(expected, StrongestExtension.strongestExtension(className, extensions));
    }
}
