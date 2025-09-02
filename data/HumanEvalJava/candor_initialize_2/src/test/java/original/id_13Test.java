package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GreatestCommonDivisor.
*/
class GreatestCommonDivisorTest {

    @Test
    void testGreatestCommonDivisor_DifferentValues_ReturnsCorrectResult() {
        int result = GreatestCommonDivisor.greatestCommonDivisor(10, 15);
        assertEquals(5, result);
    }
    
    @Test
        public void testNothing(){
            GreatestCommonDivisor s = new GreatestCommonDivisor();
            }
                                    
}