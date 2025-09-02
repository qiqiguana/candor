package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Strlen.
*/
class StrlenTest {

    @Test
    void testStrlen_WithEmptyString_ReturnsZero() {
        // Arrange & Act
        int result = Strlen.strlen(" ");
        // Assert
        assertEquals(1, result);
    }
}
