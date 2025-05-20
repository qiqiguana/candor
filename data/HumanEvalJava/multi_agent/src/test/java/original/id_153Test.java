package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of StrongestExtension.
*/
class StrongestExtensionTest {
    @Test
    void testStrongestExtension_returnClassNameWithStrongestExtension() {
        // Arrange
        String className = "my_class";
        List<String> extensions = List.of("AA", "Be", "CC");

        // Act
        String result = StrongestExtension.strongestExtension(className, extensions);

        // Assert
        assertEquals("my_class.AA", result);
    }
}