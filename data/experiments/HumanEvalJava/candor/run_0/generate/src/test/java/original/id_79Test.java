package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of DecimalToBinary.
*/
class DecimalToBinaryTest {
    @Test
    void testDecimalToBinary_ConvertsDecimalNumberToBinary() {
        String result = DecimalToBinary.decimalToBinary(15);
        assertEquals("db1111db", result);
    }
    
    @Test
        public void testNothing(){
            DecimalToBinary s = new DecimalToBinary();
            }
    @Test
    public void testDecimalToBinary_WithZeroInput_ReturnsCorrectBinaryRepresentation() {
        int input = 0;
        String expected = "db0db";
        String actual = DecimalToBinary.decimalToBinary(input);
        assertEquals(expected, actual);
    }
    @Test
    public void testDecimalToBinary_WithPositiveEvenNumber_ReturnsCorrectBinaryRepresentation() {
        int input = 32;
        String expected = "db100000db";
        String actual = DecimalToBinary.decimalToBinary(input);
        assertEquals(expected, actual);
    }
    @Test
    public void testDecimalToBinary_WithPositiveOddNumber_ReturnsCorrectBinaryRepresentation() {
        int input = 15;
        String expected = "db1111db";
        String actual = DecimalToBinary.decimalToBinary(input);
        assertEquals(expected, actual);
    }
    @Test
    public void testDecimalToBinary_WithLargeInput_ReturnsCorrectBinaryRepresentation() {
        int input = 255;
        String expected = "db11111111db";
        String actual = DecimalToBinary.decimalToBinary(input);
        assertEquals(expected, actual);
    }
                                    
}