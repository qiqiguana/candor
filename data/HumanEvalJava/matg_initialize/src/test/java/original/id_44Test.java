package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChangeBase.
*/
class ChangeBaseTest {
    @Test
    void testChangeBase_8_to_base3_should_return_22() {
        // Arrange and Act
        String actual = ChangeBase.changeBase(8, 3);
        // Assert
        assertEquals("22", actual);
    }
}