package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Modp.
*/
class ModpTest {
    @Test
    void modp() {
        int result = Modp.modp(3, 5);
        assertEquals(3, result);
    }
    
    @Test
     void testNothing(){
         Modp s = new Modp();
         }
    @Test
    public void testModP_1() {
        int result = Modp.modp(3, 5);
        assertEquals(3, result);
    }
    @Test
    public void testModP_2() {
        int result = Modp.modp(-1, 5);
        assertEquals(1, result);
    }
    @Test
    public void testModP_3() {
        int result = Modp.modp(0, 101);
        assertEquals(1, result);
    }
    @Test
    public void testModP_4() {
        int result = Modp.modp(1101, 101);
        assertEquals(2, result);
    }
                                  
}