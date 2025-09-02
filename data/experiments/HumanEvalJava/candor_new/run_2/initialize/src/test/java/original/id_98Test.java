package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpper.
*/
class CountUpperTest {
    @Test
    void testCountUpperWithEvenIndices() {
        String s = "aBCdEf";
        int expectedResult = 1;
        assertEquals(expectedResult, CountUpper.countUpper(s));
    }
}