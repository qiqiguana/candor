package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TruncateNumber.
*/
class TruncateNumberTest {
    @Test
    void testTruncateNumber_WhenCalledWithDecimal_ReturnsCorrectDecimalPart() {
        Double number = 123.456;
        Double expectedResult = 0.456;
        Double result = TruncateNumber.truncateNumber(number);
        assertEquals(expectedResult, result);
    }
    
    @Test
        void testNothing(){
            TruncateNumber s = new TruncateNumber();
            }
    @Test
    public void testTruncateSimpleDecimal() {
        Double result = TruncateNumber.truncateNumber(Double.valueOf(3.5));
        assertEquals(Double.valueOf(0.5), result);
    }
    @Test
    public void testTruncateMultipleDecimalPlaces() {
        Double result = TruncateNumber.truncateNumber(Double.valueOf(123.456));
        assertEquals(Double.valueOf(0.456), result);
    }
    @Test
    public void testTruncateNullInput() {
        assertThrows(NullPointerException.class, () -> TruncateNumber.truncateNumber(null));
    }
    @Test
    public void testTruncateZero() {
        Double result = TruncateNumber.truncateNumber(Double.valueOf(0));
        assertEquals(Double.valueOf(0), result);
    }
    @Test
    public void testTruncateRounding() {
        Double result = TruncateNumber.truncateNumber(Double.valueOf(1.333));
        assertEquals(Double.valueOf(0.333), result);
    }
    @Test
    public void testTruncateNegativeNumber() {
        Double result = TruncateNumber.truncateNumber(Double.valueOf(-3.5));
        assertEquals(Double.valueOf(0.5), Math.abs(result));
    }
                                    
}