package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing1.
*/
class CorrectBracketing1Test {
    @Test
    void correctBracketing_EmptyString_ReturnsTrue() {
        // Arrange
        String brackets = "";
        Boolean expectedResult = true;
        
        // Act
        Boolean actualResult = CorrectBracketing1.correctBracketing(brackets);
        
        // Assert
        assertEquals(expectedResult, actualResult);
    }
}