package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of XOrY.
*/
class XOrYTest {
    @Test
    void testXOrY_ReturnsY_WhenNIsNotPrime() {
        int n = 4;
        int x = 2;
        int y = 3;
        assertEquals(y, XOrY.xOrY(n, x, y));
    }
    @Test
    public void testPrimeNumberReturnsX() {
    	int n = 7;
    	int x = 34;
    	int y = 12;
    	assertEquals(x, XOrY.xOrY(n, x, y));
    }
    @Test
    public void testNonPrimeNumberReturnsY() {
    	int n = 15;
    	int x = 8;
    	int y = 5;
    	assertEquals(y, XOrY.xOrY(n, x, y));
    }
    @Test
    public void testNEqualsOne() {
    	int n = 1;
    	int x = 2;
    	int y = 0;
    	assertEquals(y, XOrY.xOrY(n, x, y));
    }
    @Test
    public void testNEqualsTwo() {
    	int n = 2;
    	int x = 2;
    	int y = 0;
    	assertEquals(x, XOrY.xOrY(n, x, y));
    }
    @Test
    public void testNegativeNumbers() {
    	int n = 7919;
    	int x = -1;
    	int y = 12;
    	assertEquals(x, XOrY.xOrY(n, x, y));
    }
    @Test
    public void testZeroInput() {
    	int n = 6;
    	int x = 0;
    	int y = 12;
    	assertEquals(y, XOrY.xOrY(n, x, y));
    }
    @Test
    public void testPrimeNumber() {
    	int n = 23;
    	int x = 34;
    	int y = 12;
    	assertEquals(x, XOrY.xOrY(n, x, y));
    }
    @Test
    public void testXOrYPrimeNumber() {
    	int result = XOrY.xOrY(7, 34, 12);
    	assertEquals(34, result);
    }
    @Test
    public void testXOrYNonPrimeNumber() {
    	int result = XOrY.xOrY(15, 8, 5);
    	assertEquals(5, result);
    }
    @Test
    public void testXOrYNegativeYValue() {
    	int result = XOrY.xOrY(7919, -1, 12);
    	assertEquals(-1, result);
    }
    @Test
    public void testXOrYXEqualToY() {
    	int result = XOrY.xOrY(2, 2, 0);
    	assertEquals(2, result);
    }
    @Test
    public void testXOrYNEqualsOne() {
    	int result = XOrY.xOrY(1, 34, 12);
    	assertEquals(12, result);
    }
    @Test
    public void Test_XOrY_PrimeNumber() {
        int result = XOrY.xOrY(7, 34, 12);
        assertEquals(34, result);
    }
    @Test
    public void Test_XOrY_NonPrimeNumber() {
        int result = XOrY.xOrY(15, 8, 5);
        assertEquals(5, result);
    }
    @Test
    public void Test_XOrY_EdgeCase_One() {
        int result = XOrY.xOrY(1, 2, 3);
        assertEquals(3, result);
    }
    @Test
    public void Test_XOrY_EdgeCase_Two() {
        int result = XOrY.xOrY(7, -34, 12);
        assertEquals(-34, result);
    }
    @Test
    public void Test_XOrY_NegativeInputs_Fixed_2() {
        int n = -15;
        int result = XOrY.xOrY(Math.abs(n), -8, -5);
        assertEquals(-5, result);
    }
}