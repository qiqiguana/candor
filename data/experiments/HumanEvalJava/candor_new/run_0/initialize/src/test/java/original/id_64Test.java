package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {
    @Test
    void testVowelCountAtEnd() {
        // Arrange and Act
        int result = VowelsCount.vowelsCount("bye");
        // Assert
        assertEquals(1, result);
    }
}
