package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Simplify.
*/
class SimplifyTest {
    @Test
    public void testSimplify() {
        String x = "1/5";
        String n = "5/1";
        assertTrue(Simplify.simplify(x, n));
    }
    @Test
    public void testSimplifyFractionTrue() {
        String x = "1/5";
        String n = "5/1";
        assertTrue(Simplify.simplify(x, n));
    }
    @Test
    public void testSimplifyFractionFalse() {
        String x = "1/6";
        String n = "2/1";
        assertFalse(Simplify.simplify(x, n));
    }
    @Test
    public void testSimplifyFractionZeroNumerator() {
        String x = "0/5";
        String n = "5/1";
        assertTrue(Simplify.simplify(x, n));
    }
    @Test
    public void testSimplifyFractionZeroDenominatorFixed1() {
    try {
    String x = "1/0";
    String n = "5/1";
    Boolean result = Simplify.simplify(x, n);
    assertNull(result);
    } catch (ArithmeticException e) {
    // expected exception
    }
    }
    @Test
    public void testSimplifyFractionIdentical() {
        String x = "1/5";
        String n = "1/5";
        assertFalse(Simplify.simplify(x, n));
    }
    @Test
    public void testSimplify_WholeNumberResult() {
        String x = "1/5";
        String n = "5/1";
        Boolean result = Simplify.simplify(x, n);
        assertTrue(result);
    }
    @Test
    public void testSimplify_NonWholeNumberResult() {
        String x = "1/6";
        String n = "2/1";
        Boolean result = Simplify.simplify(x, n);
        assertFalse(result);
    }
    @Test
    public void testSimplify_IdenticalFractions() {
        String x = "1/5";
        String n = "1/5";
        Boolean result = Simplify.simplify(x, n);
        assertFalse(result);
    }
    @Test
    public void testSimplify_NumeratorOne() {
        String x = "1/10";
        String n = "10/1";
        Boolean result = Simplify.simplify(x, n);
        assertTrue(result);
    }
    @Test
    public void testSimplify_WholeNumberResult2() {
        String x = "2/4";
        String n = "4/2";
        Boolean result = Simplify.simplify(x, n);
        assertTrue(result);
    }
    @Test
    public void test_Simplify_ValidFractions() {
    	String[] inputs = {"1/5", "5/1"};
    	assertTrue(Simplify.simplify(inputs[0], inputs[1]));
    }
    @Test
    public void test_Simplify_InvalidFraction() {
    	String[] inputs = {"a/b", "2/3"};
    	assertThrows(NumberFormatException.class, () -> Simplify.simplify(inputs[0], inputs[1]));
    }
    @Test
    public void test_Simplify_ZeroDenominator() {
    	String[] inputs = {"1/0", "2/3"};
    	assertThrows(ArithmeticException.class, () -> Simplify.simplify(inputs[0], inputs[1]));
    }
    @Test
    public void test_Simplify_SameNumeratorAndDenominator() {
    	String[] inputs = {"1/5", "1/5"};
    	assertFalse(Simplify.simplify(inputs[0], inputs[1]));
    }
    @Test
    public void test_Simplify_MultipleOfSameFraction() {
        String[] inputs = {"2/4", "8/4"};
        assertTrue(Simplify.simplify(inputs[0], inputs[1]));
    }
}