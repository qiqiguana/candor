package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of DecimalToBinary.
*/
class DecimalToBinaryTest {
    @Test
    void testDecimalToBinaryWhenInputIs15() {
        String result = DecimalToBinary.decimalToBinary(15);
        assertEquals("db1111db", result);
    }
    
    @Test
     void testNothing(){
         DecimalToBinary s = new DecimalToBinary();
         }
    @Test
    public void positiveDecimalToBinaryConversion() {
        String expected = "db1111db";
        String actual = original.DecimalToBinary.decimalToBinary(15);
        assertEquals(expected, actual);
    }
    @Test
    public void negativeInvalidInputType() {
        String input = "abc";
        assertThrows(NumberFormatException.class, () -> original.DecimalToBinary.decimalToBinary(Integer.parseInt(input)));
    }
    @Test
    public void edgeCaseZeroInput() {
    	String expected = "db0db";
    	String actual = original.DecimalToBinary.decimalToBinary(0);
    	org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
    }
                                  
}