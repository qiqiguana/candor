package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of XOrY.
*/
class XOrYTest {
    @Test
    void test_xOrY_with_prime_number(){
        // Test data with prime number 7
        int n = 7;
        int x = 34;
        int y = 12;
        assertEquals(x, XOrY.xOrY(n,x,y));
    }
    
    @Test
        public void testNothing(){
            XOrY s = new XOrY();
            }
    @Test
    public void testXOrYWithPrimeNumber() {
    	int result = XOrY.xOrY(7, 34, 12);
    	assertEquals(34, result);
    }
    @Test
    public void testXOrYWithNonPrimeNumber() {
    	int result = XOrY.xOrY(15, 8, 5);
    	assertEquals(5, result);
    }
    @Test
    public void testXOrYWithNegativeNumbers() {
    	int result = XOrY.xOrY(7919, -1, 12);
    	assertEquals(-1, result);
    }
    @Test
    public void testXOrYWithLargeNumbers() {
    	int result = XOrY.xOrY(3609, 1245, 583);
    	assertEquals(583, result);
    }
    @Test
    public void testXOrYWithEdgeCaseN1() {
    	int result = XOrY.xOrY(1, 2, 0);
    	assertEquals(0, result);
    }
    @Test
    public void testXOrYWithEdgeCaseN2() {
    	int result = XOrY.xOrY(2, 2, 0);
    	assertEquals(2, result);
    }
                                    
}