package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HowManyTimes.
*/
class HowManyTimesTest {

    @Test
    void testHowManyTimes_WhenSubstringIsFound_MustReturnCount() {
        String string = "aaaa";
        String substring = "aa";
        int expectedCount = 3;
        assertEquals(expectedCount, HowManyTimes.howManyTimes(string, substring));
    }
    
    @Test
        public void testNothing(){
            HowManyTimes s = new HowManyTimes();
            }
    @Test
    public void testEmptyString() {
        int result = HowManyTimes.howManyTimes("", "a");
        assertEquals(0, result);
    }
                                    
}