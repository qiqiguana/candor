package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Multiply.
*/
class MultiplyTest {
    @Test
    void testMultiply_ProductOfUnitDigits() {
        assertEquals(16, Multiply.multiply(148, 412));
    }
    
    @Test
        public void testNothing(){
            Multiply s = new Multiply();
            }
    @Test
    public void testMultiplyTwoPositiveNumbers() {
        int result = Multiply.multiply(148, 412);
        assertEquals(16, result);
    }
    @Test
    public void testMultiplyTwoNegativeNumbers() {
        int result = Multiply.multiply(-14, -15);
        assertEquals(20, result);
    }
    @Test
    public void testMultiplyZeroAndPositiveNumber() {
        int result = Multiply.multiply(0, 1);
        assertEquals(0, result);
    }
    @Test
    public void testMultiplyTwoZeros() {
        int result = Multiply.multiply(0, 0);
        assertEquals(0, result);
    }
    @Test
    public void testInvalidInputType() {
        try {
            Multiply.multiply(Integer.parseInt("a"), Integer.parseInt("b"));
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertTrue(e instanceof NumberFormatException);
        }
    }
                                    
}