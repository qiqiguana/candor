package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GenerateIntegers.
*/
class GenerateIntegersTest {

    @Test
    void generateIntegers_should_return_even_digits_in_ascending_order() {
        List<Object> result = GenerateIntegers.generateIntegers(2, 8);
        assertEquals(List.of(2, 4, 6, 8), result);
    }
    
    @Test
        void testNothing(){
            GenerateIntegers s = new GenerateIntegers();
            }
    @Test
    public void testLowerBound() {
        List<Object> result = GenerateIntegers.generateIntegers(0, 10);
        assertEquals(Arrays.asList(2, 4, 6, 8), result);
    }
                                    
}