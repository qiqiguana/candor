package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ClosestInteger.
*/
class ClosestIntegerTest {
    @Test
    void testClosestInteger() {
        assertEquals(15, ClosestInteger.closestInteger("14.5"));
    }
    
    @Test
        public void testNothing(){
            ClosestInteger s = new ClosestInteger();
            }
    @Test
    public void testClosestInteger_NegativeValue() {
    	String value = "-14.5";
    	int expected = -15;
    	int actual = ClosestInteger.closestInteger(value);
    	assertEquals(expected, actual);
    }
                                    
}