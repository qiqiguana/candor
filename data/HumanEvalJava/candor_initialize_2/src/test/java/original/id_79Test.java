package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of DecimalToBinary.
*/
class DecimalToBinaryTest {

    @Test
    void testDecimalToBinary_Zero() {
        String result = DecimalToBinary.decimalToBinary(0);
        assertEquals("db0db", result);
    }
    @Test
    public void testDecimalToBinaryConversionForPositiveNumbers() {
        String result = DecimalToBinary.decimalToBinary(15);
        assertEquals("db1111db", result);
    }
    @Test
    public void testDecimalToBinaryConversionForZero() {
        String result = DecimalToBinary.decimalToBinary(0);
        assertEquals("db0db", result);
    }
    @Test
    public void testDecimalToBinaryConversionForLargeNumbers() {
        String result = DecimalToBinary.decimalToBinary(1024);
        assertEquals("db10000000000db", result);
    }
    @Test
    public void testDecimalToBinaryConversionForEdgeCaseWithStringBuilder2() {
        int input = 2147483647;
        String result = DecimalToBinary.decimalToBinary(input);
        StringBuilder expectedBuilder = new StringBuilder();
        while (input > 0) {
            expectedBuilder.insert(0, input % 2);
            input /= 2;
        }
        String expected = "db" + expectedBuilder.toString() + "db";
        assertEquals(expected, result);
    }
    @Test
    public void testDecimalToBinaryWithZeroInput() {
        assertEquals("db0db", DecimalToBinary.decimalToBinary(0));
    }
    @Test
    public void testDecimalToBinaryWithPositiveEvenNumber() {
        assertEquals("db100000db", DecimalToBinary.decimalToBinary(32));
    }
    @Test
    public void testDecimalToBinaryWithPositiveOddNumber() {
        assertEquals("db1100111db", DecimalToBinary.decimalToBinary(103));
    }
    @Test
    public void testZeroDecimalValue() {
    	String result = DecimalToBinary.decimalToBinary(0);
    	assertEquals("db0db", result);
    }
    @Test
    public void testSmallDecimalValue() {
    	String result = DecimalToBinary.decimalToBinary(15);
    	assertEquals("db1111db", result);
    }
    @Test
    public void testMediumDecimalValue() {
    	String result = DecimalToBinary.decimalToBinary(32);
    	assertEquals("db100000db", result);
    }
    @Test
    public void testLargeDecimalValue() {
    	String result = DecimalToBinary.decimalToBinary(103);
    	assertEquals("db1100111db", result);
    }
    @Test
    public void testMaxDecimalValue() {
    	String result = DecimalToBinary.decimalToBinary(Integer.MAX_VALUE);
    	assertNotNull(result);
    }
}