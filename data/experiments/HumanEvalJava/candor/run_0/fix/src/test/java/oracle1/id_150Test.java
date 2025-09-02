package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of XOrY.
*/
class XOrYTest {
    @Test
    void testXOrY_NIsPrime_ReturnsX() {
        int n = 7;
        int x = 34;
        int y = 12;
        assertEquals(x, XOrY.xOrY(n, x, y));
    }
    
    @Test
     void testNothing(){
         XOrY s = new XOrY();
         }
    @Test
    public void testPrimeNumberReturnsX() {
        assertEquals(34, XOrY.xOrY(7, 34, 12));
    }
    @Test
    public void testNonPrimeNumberReturnsY() {
        assertEquals(5, XOrY.xOrY(15, 8, 5));
    }
    @Test
    public void testNEqual1ReturnsY() {
        assertEquals(0, XOrY.xOrY(1, 2, 0));
    }
    @Test
    public void testNEqual2ReturnsX() {
        assertEquals(2, XOrY.xOrY(2, 2, 0));
    }
    @Test
    public void testNegativeNReturnsY() {
        assertEquals(52, XOrY.xOrY(-1, 3, 52));
    }
                                  
}