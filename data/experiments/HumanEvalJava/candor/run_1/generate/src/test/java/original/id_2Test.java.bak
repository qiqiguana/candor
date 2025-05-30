package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TruncateNumber.
*/
class TruncateNumberTest {
    @Test
    void testTruncateNumber() {
        Double number = 123.456;
        Double expected = 0.456;
        assertEquals(expected, TruncateNumber.truncateNumber(number));
    }
    
    @Test
        public void testNothing(){
            TruncateNumber s = new TruncateNumber();
            }
    @Test
    public void TestTruncateNumberPositive() {
    	Double number = 3.5;
    	assertEquals(0.5, TruncateNumber.truncateNumber(number), 0);
    }
    @Test
    public void TestTruncateNumberEdgeCase() {
    	Double number = 10.0;
    	assertEquals(0.0, TruncateNumber.truncateNumber(number), 0);
    }
    @Test
    public void TestTruncateNumberSpecificFunctionality() {
    	Double number = 123.456789;
    	assertEquals(0.457, TruncateNumber.truncateNumber(number), 0);
    }
    @Test
    public void TestTruncateNumberNullInput() {
    	Double number = null;
    	assertThrows(NullPointerException.class, () -> TruncateNumber.truncateNumber(number));
    }
                                    
}