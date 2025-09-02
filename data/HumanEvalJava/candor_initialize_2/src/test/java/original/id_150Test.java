package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of XOrY.
*/
class XOrYTest {
    @Test
    void testXOrY_Returns_X_For_Prime_Number() {
        int result = XOrY.xOrY(7, 34, 12);
        assertEquals(34, result);
    }
    @Test
    public void test_xOrY_PrimeNumber() {
    	int n = 7;
    	int x = 34;
    	int y = 12;
    	assertEquals(34, XOrY.xOrY(n, x, y));
    }
    @Test
    public void test_xOrY_NonPrimeNumber() {
    	int n = 15;
    	int x = 8;
    	int y = 5;
    	assertEquals(5, XOrY.xOrY(n, x, y));
    }
    @Test
    public void test_xOrY_NEqualsOne() {
    	int n = 1;
    	int x = 33;
    	int y = 5212;
    	assertEquals(5212, XOrY.xOrY(n, x, y));
    }
    @Test
    public void test_xOrY_NEqualsTwo() {
    	int n = 2;
    	int x = 2;
    	int y = 0;
    	assertEquals(2, XOrY.xOrY(n, x, y));
    }
    @Test
    public void test_xOrY_InvalidInput() {
    	int n = 0;
    	int x = 34;
    	int y = 12;
    	// No assertion, since the function does not specify its behavior for invalid input
    }
    @Test
    public void test_xOrY_with_prime_number() {
        int result = XOrY.xOrY(7, 34, 12);
        assertEquals(34, result);
    }
    @Test
    public void test_xOrY_with_non_prime_number() {
        int result = XOrY.xOrY(15, 8, 5);
        assertEquals(5, result);
    }
    @Test
    public void test_xOrY_with_edge_case_n_equals_1() {
        int result = XOrY.xOrY(1, 2, 0);
        assertEquals(0, result);
    }
    @Test
    public void test_xOrY_with_negative_number() {
        int result = XOrY.xOrY(7919, -1, 12);
        assertEquals(-1, result);
    }
    @Test
    public void test_xOrY_with_large_numbers() {
        int result = XOrY.xOrY(1259, 3, 52);
        assertEquals(3, result);
    }
    @Test
    public void test_xOrY_with_n_equals_2() {
        int result = XOrY.xOrY(2, 1, 3);
        assertEquals(1, result);
    }
    @Test
    void test_xOrY_with_prime_number_1() {
        int result = XOrY.xOrY(7, 34, 12);
        assertEquals(34, result);
    }
    @Test
    void test_xOrY_with_non_prime_number_1() {
        int result = XOrY.xOrY(4, 8, 5);
        assertEquals(5, result);
    }
    @Test
    void test_xOrY_with_edge_case_n_1() {
        int result = XOrY.xOrY(1, 2, 0);
        assertEquals(0, result);
    }
    @Test
    void test_xOrY_with_edge_case_n_2() {
        int result = XOrY.xOrY(2, 2, 0);
        assertEquals(2, result);
    }
    @Test
    void test_xOrY_with_large_prime_number() {
        int result = XOrY.xOrY(12539, 34, 12);
        assertEquals(34, result);
    }
    @Test
    void test_xOrY_with_non_prime_number2() {
        int result = XOrY.xOrY(15, 8, 5);
        assertEquals(5, result);
    }
}