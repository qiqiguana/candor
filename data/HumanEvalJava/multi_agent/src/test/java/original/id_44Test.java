package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChangeBase.
*/
class ChangeBaseTest {

    @Test
    void testChangeBase_SingleDigit() {
        // Arrange
        int input1 = 5;
        int input2 = 6;
        
        // Act
        String result = ChangeBase.changeBase(input1, input2);
        
        // Assert
        assertEquals("5", result);
    }
}