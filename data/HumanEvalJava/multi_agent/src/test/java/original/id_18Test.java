package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HowManyTimes.
*/
class HowManyTimesTest {
    @Test
    void testHowManyTimes_WhenSubstringIsFound_ReturnsCorrectCount() {
        String string = "aaa";
        String substring = "a";
        int expectedResult = 3;
        assertEquals(expectedResult, HowManyTimes.howManyTimes(string, substring));
    }
}