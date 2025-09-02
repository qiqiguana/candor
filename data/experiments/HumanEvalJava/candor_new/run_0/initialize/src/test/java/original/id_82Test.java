package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeLength.
*/
class PrimeLengthTest {
    @Test
    void primeLength_should_Return_False_For_Empty_String() {
        // Arrange and Act
        boolean result = PrimeLength.primeLength("");
        // Assert
        assertFalse(result);
    }
}