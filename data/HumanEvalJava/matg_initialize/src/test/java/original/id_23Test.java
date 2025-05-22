package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Strlen.
*/
class StrlenTest {
    @Test
    void test_strlen_of_empty_string() {
        // Arrange and Act
        int result = Strlen.strlen("");
        // Assert
        assertEquals(0, result);
    }
}
