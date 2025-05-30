package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SpecialFactorial.
*/
class SpecialFactorialTest {
    @Test
    void testSpecialFactorial_SimpleCase() {
        long result = SpecialFactorial.specialFactorial(4);
        assertEquals(288, result);
    }
    
    @Test
        public void testNothing(){
            SpecialFactorial s = new SpecialFactorial();
            }
    @Test
    public void SpecialFactorial_HappyPath_2() {
    	int n = 1;
    	long result = SpecialFactorial.specialFactorial(n);
    	assertEquals(1L, result);
    }
                                    
}