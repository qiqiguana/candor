package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TruncateNumber.
*/
class TruncateNumberTest {
    @Test
    void testTruncateNumber()
    {
        Double result = TruncateNumber.truncateNumber(3.5);
        assertEquals(result, 0.5, 1e-9);
    }
    
    @Test
        public void testNothing(){
            TruncateNumber s = new TruncateNumber();
            }
                                    
}