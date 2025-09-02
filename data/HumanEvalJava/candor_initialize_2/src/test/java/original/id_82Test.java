package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeLength.
*/
class PrimeLengthTest {
    @Test
    void testPrimeLength_EmptyString_ReturnsFalse() {
        // Arrange
        String input = "";
        Boolean expectedResult = false;

        // Act
        Boolean actualResult = PrimeLength.primeLength(input);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            PrimeLength s = new PrimeLength();
            }
    @Test
    public void testPrimeLength_WithEmptyString_ReturnsFalse() {
        assertFalse(PrimeLength.primeLength(""));
    }
    @Test
    public void testPrimeLength_WithSingleCharacterString_ReturnsFalse() {
        assertFalse(PrimeLength.primeLength("a"));
    }
    @Test
    public void testPrimeLength_WithPrimeLengthString_ReturnsTrue() {
        assertTrue(PrimeLength.primeLength("abcdefg"));
    }
    @Test
    public void testPrimeLength_WithCorrectedPrimeLengthString_ReturnsTrue() {
        assertTrue(PrimeLength.primeLength("abcde"));
    }
    @Test
    public void testPrimeLength_WithLargePrimeLengthString_ReturnsTrue() { assertTrue(PrimeLength.primeLength("abcdefghijk")); }
    @Test
    public void testNonPrimeLength() {
      String input = new String("nonprime");
      assertFalse(PrimeLength.primeLength(input));
    }
                                    
}