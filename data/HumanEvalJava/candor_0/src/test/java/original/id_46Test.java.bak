package original;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fib4.
*/
class Fib4Test {
    @Test
    void testFib4_baseCase() {
        assertEquals(0, Fib4.fib4(0));
    }
    
    @Test
        public void testNothing(){
            Fib4 s = new Fib4();
            }
    @Test
    public void TestFib4_HappyPath_0() {
    	assertEquals(0, Fib4.fib4(0));
    }
    @Test
    public void TestFib4_HappyPath_1() {
    	assertEquals(0, Fib4.fib4(1));
    }
    @Test
    public void TestFib4_HappyPath_2() {
    	assertEquals(2, Fib4.fib4(2));
    }
    @Test
    public void TestFib4_HappyPath_3() {
    	assertEquals(0, Fib4.fib4(3));
    }
    @Test
    public void TestFib4_SadCase_Negative() {
    	assertEquals(0, Fib4.fib4(-1));
    }
    @Test
    public void test_fib4_0() {
        assertEquals(0, Fib4.fib4(0));
    }
    @Test
    public void test_fib4_1() {
        assertEquals(0, Fib4.fib4(1));
    }
    @Test
    public void test_fib4_2() {
        assertEquals(2, Fib4.fib4(2));
    }
    @Test
    public void test_fib4_3() {
        assertEquals(0, Fib4.fib4(3));
    }
    @Test
    public void TestFib4_LargeInput() {
        int expected = 104;
        int actual = Fib4.fib4(10);
        assertEquals(expected, actual);
    }
    @Test
    public void TestFib4_EdgeCase_LargeInput3() {
        BigInteger expected = new BigInteger("52080824");
        BigInteger actual = fib4BigInteger(30);
        assertEquals(expected, actual);
    }
    @Test
    public void TestFib4_EdgeCase_LargeInput3_New() {
        long expected = 52080824;
        int actual = Fib4.fib4(30);
        assertEquals(expected, (long)actual);
    }
    @Test
    public void test_fib4_5_corrected() {
        assertEquals(4, Fib4.fib4(5));
    }
    @Test
    public void test_fib4_6_corrected() {
        assertEquals(8, Fib4.fib4(6));
    }
    @Test
    public void test_fib4_7() {
        assertEquals(14, Fib4.fib4(7));
    }
    
    public static BigInteger fib4BigInteger(int n) {
        if (n < 2) {
            return BigInteger.ZERO;
        }
        if (n == 2) {
            return BigInteger.valueOf(2);
        }
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ZERO;
        BigInteger c = BigInteger.valueOf(2);
        BigInteger d = BigInteger.ZERO;
        for (int i = 4; i <= n; i++) {
            BigInteger e = a.add(b).add(c).add(d);
            a = b;
            b = c;
            c = d;
            d = e;
        }
        return d;
    }
                                    
}