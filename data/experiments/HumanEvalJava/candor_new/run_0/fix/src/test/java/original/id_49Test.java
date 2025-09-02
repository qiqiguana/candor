package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Modp.
*/
class ModpTest {

    @Test
    void testModP_WhenInputIs0_ResultShouldBe1() {
        int result = Modp.modp(0, 101);
        assertEquals(1, result);
    }
    
    @Test
        public void testNothing(){
            Modp s = new Modp();
            }
    @Test
    public void testModpFunctionality() {
        int result = Modp.modp(3, 5);
        assertEquals(3, result);
    }
                                    
}