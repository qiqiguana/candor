package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IntToMiniRoman.
*/
class IntToMiniRomanTest {
    @Test
    void testIntToMiniRoman_ConvertNumberToMiniRoman() {
        // Arrange and Act
        String result = IntToMiniRoman.intToMiniRoman(19);
        // Assert
        assertEquals("xix", result);
    }
    
    @Test
        public void testNothing(){
            IntToMiniRoman s = new IntToMiniRoman();
            }
                                    
}