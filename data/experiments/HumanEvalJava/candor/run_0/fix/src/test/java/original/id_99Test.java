package original;

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
     void testNothing(){
         ClosestInteger s = new ClosestInteger();
         }
    @Test
    public void testClosestInteger_Positive_Integer() {
        String input = "10";
        int expectedResult = 10;
        int actualResult = ClosestInteger.closestInteger(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_Positive_Decimal_RoundedDown() {
        String input = "15.3";
        int expectedResult = 15;
        int actualResult = ClosestInteger.closestInteger(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_Negative_Integer() {
        String input = "-10";
        int expectedResult = -10;
        int actualResult = ClosestInteger.closestInteger(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_Negative_Decimal_RoundedDown() {
        String input = "-15.3";
        int expectedResult = -15;
        int actualResult = ClosestInteger.closestInteger(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_Zero() {
        String input = "0";
        int expectedResult = 0;
        int actualResult = ClosestInteger.closestInteger(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_Decimal_RoundedAwayFromZero() {
        String input = "14.5";
        int expectedResult = 15;
        int actualResult = ClosestInteger.closestInteger(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testClosestInteger_Decimal_RoundedAwayFromZero_Negative() {
        String input = "-14.5";
        int expectedResult = -15;
        int actualResult = ClosestInteger.closestInteger(input);
        assertEquals(expectedResult, actualResult);
    }
                                  
}