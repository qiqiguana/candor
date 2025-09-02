package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HowManyTimes.
*/
class HowManyTimesTest {
    @Test
    void testHowManyTimes_OverlapCases() {
        assertEquals(3, HowManyTimes.howManyTimes("aaa", "a"));
    }
    
    @Test
        public void testNothing(){
            HowManyTimes s = new HowManyTimes();
            }
    @Test
    public void testHowManyTimes_withOverlappingMatches() {
        String string = "aaaa";
        String substring = "aa";
        int expectedResult = 3;
        int result = HowManyTimes.howManyTimes(string, substring);
        assertEquals(expectedResult, result);
    }
                                    
}