package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Iscube.
*/
class IscubeTest {
    @Test
    void testIscube_ForZero_ReturnsTrue() {
        // Arrange and Act
        boolean result = Iscube.iscube(0);
        
        // Assert
        assertTrue(result);
    }
}