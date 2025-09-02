package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IntToMiniRoman.
*/
class IntToMiniRomanTest {
    @Test
    void testIntToMiniRoman_ConversionOfWorkingValues_ReturnsCorrectRomanNumeral() {
        // Arrange
        int number = 426;
        String expected = "cdxxvi";
        
        // Act
        String result = IntToMiniRoman.intToMiniRoman(number);
        
        // Assert
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            IntToMiniRoman s = new IntToMiniRoman();
            }
                                    
}