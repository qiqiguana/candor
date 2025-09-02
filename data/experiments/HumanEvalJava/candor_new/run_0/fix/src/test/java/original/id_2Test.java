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
        assertEquals(0.456, TruncateNumber.truncateNumber(number), 1e-9);
    }
    
    @Test
        public void testNothing(){
            TruncateNumber s = new TruncateNumber();
            }
                                    
}