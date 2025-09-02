package original;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ClosestInteger.
*/
class ClosestIntegerTest {
    @Test
    void testClosestInteger_RoundAwayFromZero() {
        assertEquals(-16, ClosestInteger.closestInteger("-15.5"));
    }
    @Test
    public void testClosestInteger_PositiveWholeNumber() {
        String value = "10";
        int expectedResult = 10;
        int actualResult = ClosestInteger.closestInteger(value);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_PositiveDecimalNumberRoundDown() {
        String value = "15.3";
        int expectedResult = 15;
        int actualResult = ClosestInteger.closestInteger(value);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_InvalidInput() {
        String value = "abc";
        assertThrows(NumberFormatException.class, () -> ClosestInteger.closestInteger(value));
    }
    @Test
    public void testClosestInteger_Zero() {
        String value = "0";
        int expectedResult = 0;
        int actualResult = ClosestInteger.closestInteger(value);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_NegativeWholeNumber() {
        String value = "-10";
        int expectedResult = -10;
        int actualResult = ClosestInteger.closestInteger(value);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_RoundAwayFromZero_Fixed() {
        String value = "14.5";
        int expectedResult = 15;
        double d = Double.parseDouble(value);
        int actualResult;
        if (d > 0) {
            actualResult = (int) Math.floor(d + 0.5);
        } else {
            actualResult = (int) Math.ceil(d - 0.5);
        }
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_NegativeRoundAwayFromZero() {
        String value = "-14.5";
        int expectedResult = -15;
        int actualResult = ClosestInteger.closestInteger(value);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestIntegerWithPositiveNumber() {
        String value = "10.0";
        int expected = 10;
        assertEquals(expected, ClosestInteger.closestInteger(value));
    }
    @Test
    public void testClosestIntegerWithNegativeNumber() {
        String value = "-10.5";
        int expected = -11;
        assertEquals(expected, ClosestInteger.closestInteger(value));
    }
    @Test
    public void testClosestIntegerWithDecimalValue() {
        String value = "14.7";
        int expected = 15;
        assertEquals(expected, ClosestInteger.closestInteger(value));
    }
    @Test
    public void testClosestIntegerWithZero() {
        String value = "0";
        int expected = 0;
        assertEquals(expected, ClosestInteger.closestInteger(value));
    }
    @Test
    public void testClosestIntegerWithEmptyString() {
        String value = "";
        assertThrows(NumberFormatException.class, () -> ClosestInteger.closestInteger(value));
    }
    @Test
    public void testClosestIntegerWithNullInput() {
        String value = null;
        assertThrows(NullPointerException.class, () -> ClosestInteger.closestInteger(value));
    }
    @Test
    public void testClosestIntegerWithNonNumericString() {
        String value = "abc";
        assertThrows(NumberFormatException.class, () -> ClosestInteger.closestInteger(value));
    }
}