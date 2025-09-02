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
    void testCountUpTo_WhenInputIs5_ReturnsPrimeNumbersLessThan5() {
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);
        assertEquals(expected, CountUpTo.countUpTo(5));
    }
    
    @Test
        public void testNothing(){
            CountUpTo s = new CountUpTo();
            }
                                    
}