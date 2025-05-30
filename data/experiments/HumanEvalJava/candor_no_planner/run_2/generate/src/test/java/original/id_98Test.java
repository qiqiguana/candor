package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpper.
*/
class CountUpperTest {
    @Test
    void testCountUpper_EvenIndex_UpperCaseVowel_ReturnsOne() {
        String input = "aBCdEf";
        int expected = 1;
        assertEquals(expected, CountUpper.countUpper(input));
    }
    
    @Test
        public void testNothing(){
            CountUpper s = new CountUpper();
            }
                                    
}