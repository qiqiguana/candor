package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeFib.
*/
class PrimeFibTest {
    @Test
    void testPrimeFib() {
        assertEquals(2, PrimeFib.primeFib(1));
    }
    
    @Test
        public void testNothing(){
            PrimeFib s = new PrimeFib();
            }
    @Test
    void testPrimeFib_with_n_equal_10() {
    	int result = PrimeFib.primeFib(10);
    	assertEquals(433494437, result);
    }
                                    
}