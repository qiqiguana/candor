package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FlipCase.
*/
class FlipCaseTest {
    @Test
    void testFlipCase_flips_upper_and_lower_case_letters() {
        String result = FlipCase.flipCase("Hello");
        assertEquals("hELLO", result);
    }
    
    @Test
        public void testNothing(){
            FlipCase s = new FlipCase();
            }
                                    
}