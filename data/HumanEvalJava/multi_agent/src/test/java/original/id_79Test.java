package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of DecimalToBinary.
*/
class DecimalToBinaryTest {
    @Test
    void testDecimalToBinary_withZeroInput_shouldReturn_db0db() {
        String result = DecimalToBinary.decimalToBinary(0);
        assertEquals("db0db", result);
    }
    @Test
    public void testLargeInput() {
        String result = DecimalToBinary.decimalToBinary(1024);
        assertEquals("db10000000000db", result);
    }
    @Test
    public void testLoopEntryWithPositiveDecimalValue() {
        String result = DecimalToBinary.decimalToBinary(10);
        assertEquals("db1010db", result);
    }
    @Test
    public void testBinaryDigitCalculation() {
        String result = DecimalToBinary.decimalToBinary(15);
        assertEquals("db1111db", result);
    }
    @Test
    public void testFinalBinaryStringConstructionWithPrefixAndSuffixForLargeInput() {
        String result = DecimalToBinary.decimalToBinary(255);
        assertEquals("db11111111db", result);
    }
    public void testDecimalToBinaryWithInput103() {
            assertEquals("db1100111db", DecimalToBinary.decimalToBinary(103));
        }
    public void testDecimalToBinaryWithInput32() {
            assertEquals("db100000db", DecimalToBinary.decimalToBinary(32));
        }
    public void testDecimalToBinaryWithInput15() {
            assertEquals("db1111db", DecimalToBinary.decimalToBinary(15));
        }
    public void testDecimalToBinaryWithInput0() {
            assertEquals("db0db", DecimalToBinary.decimalToBinary(0));
        }
    public void testDecimalToBinaryConversionWith103Input() {
        int input = 103;
        String expectedResult = "db1100111db";
        String result = DecimalToBinary.decimalToBinary(input);
        assertEquals(expectedResult, result);
    }
    public void testDecimalToBinaryConversionWith16Input() {
        int input = 16;
        String expectedResult = "db10000db";
        String result = DecimalToBinary.decimalToBinary(input);
        assertEquals(expectedResult, result);
    }
}