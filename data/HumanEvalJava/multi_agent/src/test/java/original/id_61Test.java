package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing1.
*/
class CorrectBracketing1Test {
    @Test
    void testCorrectBracketing_EmptyString_ReturnsTrue() {
        // Arrange
        String brackets = "";

        // Act
        Boolean result = CorrectBracketing1.correctBracketing(brackets);

        // Assert
        assertTrue(result);
    }
}