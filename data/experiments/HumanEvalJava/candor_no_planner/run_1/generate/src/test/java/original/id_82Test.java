package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeLength.
*/
class PrimeLengthTest {
    @Test
    void primeLength_Hello_ReturnsTrue() {
        // Arrange and Act
        boolean result = PrimeLength.primeLength("Hello");
        
        // Assert
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            PrimeLength s = new PrimeLength();
            }
    @Test
    public void testPrimeLengthWithEmptyString() {
        assertFalse(PrimeLength.primeLength(""));
    }
    @Test
    public void testPrimeLengthWithSingleCharacter() {
        assertFalse(PrimeLength.primeLength("M"));
    }
    @Test
    public void testPrimeLengthWithNonPrimeLength() {
        assertFalse(PrimeLength.primeLength("gogo"));
    }
                                    
}