package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ClosestInteger.
*/
class ClosestIntegerTest {

    @Test
    void test_closestInteger_RoundsAwayFromZero() {
        assertEquals(-16, ClosestInteger.closestInteger("-15.5"));
    }
    @Test
    public void testClosestInteger_HappyPath() {
        String[] inputs = {"10", "15.3", "14.5", "-14.5"};
        int[] expectedResults = {10, 15, 15, -15};
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(expectedResults[i], ClosestInteger.closestInteger(inputs[i]));
        }
    }
    @Test
    public void testClosestInteger_InvalidInput() {
        String input = "abc";
        assertThrows(NumberFormatException.class, () -> ClosestInteger.closestInteger(input));
    }
    @Test
    public void testClosestInteger_ZeroInput() {
        String input = "0";
        int expectedResult = 0;
        assertEquals(expectedResult, ClosestInteger.closestInteger(input));
    }
    @Test
    public void testClosestInteger_LargePositiveNumber() {
        String input = "123456789.5";
        int expectedResult = 123456790;
        assertEquals(expectedResult, ClosestInteger.closestInteger(input));
    }
    @Test
    public void testClosestInteger_LargeNegativeNumber() {
        String input = "-123456789.5";
        int expectedResult = -123456790;
        assertEquals(expectedResult, ClosestInteger.closestInteger(input));
    }
    @Test
    public void testPositiveInteger() {
        assertEquals(10, ClosestInteger.closestInteger("10"));
    }
    @Test
    public void testNegativeInteger() {
        assertEquals(-10, ClosestInteger.closestInteger("-10"));
    }
    @Test
    public void testPositiveDecimalAwayFromZero() {
        assertEquals(15, ClosestInteger.closestInteger("15.3"));
    }
    @Test
    public void testNegativeDecimalAwayFromZeroFixed() {
        assertEquals(-16, ClosestInteger.closestInteger("-15.5"));
    }
    @Test
    public void testPositiveDecimalTowardsZero() {
        assertEquals(15, ClosestInteger.closestInteger("14.5"));
    }
    @Test
    public void testNegativeDecimalTowardsZero() {
        assertEquals(-15, ClosestInteger.closestInteger("-14.5"));
    }
    @Test
    public void testZero() {
        assertEquals(0, ClosestInteger.closestInteger("0"));
    }
    @Test
    public void testClosestInteger_Positive_IntegerValue() {
        int result = ClosestInteger.closestInteger("10");
        assertEquals(10, result);
    }
    @Test
    public void testClosestInteger_NonNumericValue() {
        assertThrows(NumberFormatException.class, () -> ClosestInteger.closestInteger("abc"));
    }
    @Test
    public void testClosestInteger_ZeroValue() {
        int result = ClosestInteger.closestInteger("0");
        assertEquals(0, result);
    }
    @Test
    public void testClosestInteger_Positive_DecimalValueAwayFromZero() {
        int result = ClosestInteger.closestInteger("14.5");
        assertEquals(15, result);
    }
    @Test
    public void testClosestInteger_Negative_DecimalValueTowardZero() {
        int result = ClosestInteger.closestInteger("-14.5");
        assertEquals(-15, result);
    }
    @Test
    public void testClosestInteger_LargePositiveValue() {
        int result = ClosestInteger.closestInteger("1000000.3");
        assertEquals(1000000, result);
    }
    @Test
    public void testClosestInteger_LargeNegativeValue() {
        int result = ClosestInteger.closestInteger("-1000000.3");
        assertEquals(-1000000, result);
    }
}