package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TruncateNumber.
*/
class TruncateNumberTest {
    @Test
    void test_truncateNumber() {
        Double number = 123.456;
        Double expectedDecimalPart = 0.456;
        assertEquals(expectedDecimalPart, TruncateNumber.truncateNumber(number));
    }
    
    @Test
        public void testNothing(){
            TruncateNumber s = new TruncateNumber();
            }
                                    
}