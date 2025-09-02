package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChangeBase.
*/
class ChangeBaseTest {
    @Test
    void testChangeBase_singleDigitNumber() {
        int x = 7;
        int base = 2;
        String expected = "111";
        String actual = ChangeBase.changeBase(x, base);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            ChangeBase s = new ChangeBase();
            }
                                    
}