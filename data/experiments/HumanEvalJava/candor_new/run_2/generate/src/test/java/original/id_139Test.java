package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SpecialFactorial.
*/
class SpecialFactorialTest {

    @Test
    void testSpecialFactorial_ForPositiveNumber_ReturnsCorrectResult() {
        // Arrange and Act
        long result = SpecialFactorial.specialFactorial(4);
        // Assert
        assertEquals(288, result);
    }
    
    @Test
        public void testNothing(){
            SpecialFactorial s = new SpecialFactorial();
            }
                                    
}