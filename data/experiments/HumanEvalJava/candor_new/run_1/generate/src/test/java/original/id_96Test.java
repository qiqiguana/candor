package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of CountUpTo.
*/
class CountUpToTest {
    @Test
    void testCountUpTo_ReturnsPrimeNumbersLessThanInput() {
        int n = 10;
        List<Object> result = CountUpTo.countUpTo(n);
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
        assertTrue(result.contains(5));
        assertTrue(result.contains(7));
    }
    
    @Test
        public void testNothing(){
            CountUpTo s = new CountUpTo();
            }
                                    
}