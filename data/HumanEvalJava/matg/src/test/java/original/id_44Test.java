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
        void testNothing(){
            ChangeBase s = new ChangeBase();
            }
    @Test
    public void testChangeBaseWithBaseLessThan10() {
        String result = ChangeBase.changeBase(8, 3);
        assertEquals("22", result);
    }
    @Test
    public void testChangeBaseWithBaseEqualTo2() {
        String result = ChangeBase.changeBase(8, 2);
        assertEquals("1000", result);
    }
    @Test
    public void testChangeBaseWithXLessThanBase() {
        String result = ChangeBase.changeBase(7, 8);
        assertEquals("7", result);
    }
    @Test
    public void testChangeBaseWithXEqualToBase() {
        String result = ChangeBase.changeBase(8, 8);
        assertEquals("10", result);
    }
    @Test
    public void testChangeBaseWithXGreaterThanBase() {
        String result = ChangeBase.changeBase(16, 2);
        assertEquals("10000", result);
    }
                                    
}