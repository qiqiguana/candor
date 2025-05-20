package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Multiply.
*/
class MultiplyTest {
    @Test
    void testMultiply() {
        assertEquals(16, Multiply.multiply(148, 412));
    }
    
    @Test
        void testNothing(){
            Multiply s = new Multiply();
            }
    @Test
    public void testMultiply_PositiveNumbers() {
    	int result = Multiply.multiply(148, 412);
    	assertEquals(16, result);
    }
    @Test
    public void testMultiply_NegativeNumbers() {
    	int result = Multiply.multiply(-19, -28);
    	assertEquals(72, result);
    }
    @Test
    public void testMultiply_OneZero() {
    	int result = Multiply.multiply(0, 1);
    	assertEquals(0, result);
    }
    @Test
    public void testMultiply_LargeNumbers() {
    	int result = Multiply.multiply(2020, 1851);
    	assertEquals(0, result);
    }
    @Test
    public void testMultiply_BothZero() {
    	int result = Multiply.multiply(0, 0);
    	assertEquals(0, result);
    }
                                    
}