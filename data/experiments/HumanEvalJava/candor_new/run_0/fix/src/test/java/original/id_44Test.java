package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChangeBase.
*/
class ChangeBaseTest {
    @Test
    void testChangeBase_SimpleConversion() {
        int x = 8;
        int base = 2;
        String expected = "1000";
        String actual = ChangeBase.changeBase(x, base);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            ChangeBase s = new ChangeBase();
            }
                                    
}