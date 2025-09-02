package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Iscube.
*/
class IscubeTest {
    @Test
    void testIscube_CorrectInput_ReturnsExpectedResult() {
        // Arrange and Act
        boolean result = Iscube.iscube(64);
        // Assert
        assertTrue(result);
    }
}