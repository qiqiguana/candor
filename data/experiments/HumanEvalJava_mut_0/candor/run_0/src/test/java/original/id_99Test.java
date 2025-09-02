package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ClosestInteger.
*/
class ClosestIntegerTest {
    @Test
    void test_closestInteger_PositiveNumber() {
        String value = "10.7";
        int expectedResult = 11;
        int actualResult = ClosestInteger.closestInteger(value);
        assertEquals(expectedResult, actualResult);
    }
}