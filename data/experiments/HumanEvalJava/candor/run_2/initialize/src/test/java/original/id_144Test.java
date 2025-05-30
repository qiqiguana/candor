package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Simplify.
*/
class SimplifyTest {
    @Test
    void test_simplify() {
        String x = "1/5";
        String n = "5/1";
        Boolean result = Simplify.simplify(x, n);
        assertTrue(result);
    }
    @Test
    public void simplifyTestCase1() {
        String x = "1/5";
        String n = "5/1";
        assertTrue(Simplify.simplify(x, n));
    }
    @Test
    public void simplifyTestCase3() {
        String x = "2/4";
        String n = "8/4";
        assertTrue(Simplify.simplify(x, n));
    }
    @Test
    public void simplifyTestCase5() {
        String x = "1.5/5";
        String n = "5/1";
        try {
            Simplify.simplify(x, n);
            fail("Expected NumberFormatException");
        } catch (NumberFormatException e) {}
    }
    @Test
    public void simplifyTestCase6() {
        String x = "1/5";
        String n = "1/5";
        assertFalse(Simplify.simplify(x, n));
    }
    @Test
    public void testSimplifyWholeNumberResult() {
        boolean result = Simplify.simplify("1/5", "5/1");
        assertTrue(result);
    }
    @Test
    public void testSimplifyNonWholeNumberResult() {
        boolean result = Simplify.simplify("1/6", "2/1");
        assertFalse(result);
    }
    @Test
    public void testSimplifyIdenticalFractions() {
        boolean result = Simplify.simplify("1/5", "1/5");
        assertFalse(result);
    }
    @Test
    public void testSimplifyLargeNumbers() {
        boolean result = Simplify.simplify("1234/5678", "9012/3456");
        assertFalse(result);
    }
    @Test
    public void testSimplifyReducedFractions() {
        boolean result = Simplify.simplify("1/2", "3/4");
        assertFalse(result);
    }
    @Test
    public void testSimplifyFraction() {
        String x = "1/5";
        String n = "5/1";
        boolean result = Simplify.simplify(x, n);
        assertTrue(result);
    }
    @Test
    public void testNonSimplifiableFraction() {
        String x = "1/6";
        String n = "2/1";
        boolean result = Simplify.simplify(x, n);
        assertFalse(result);
    }
    @Test
    public void testZeroDenominator() {
        String x = "1/0";
        String n = "5/1";
        assertThrows(ArithmeticException.class, () -> Simplify.simplify(x, n));
    }
    @Test
    public void testSimplifyMethodWithLCM() {
        String x = "2/3";
        String n = "1/1";
        boolean result = Simplify.simplify(x, n);
        assertFalse(result);
    }
    @Test
    public void testSimplifyFraction2() {
        String x = "3/4";
        String n = "4/3";
        boolean result = Simplify.simplify(x, n);
        assertTrue(result);
    }
}