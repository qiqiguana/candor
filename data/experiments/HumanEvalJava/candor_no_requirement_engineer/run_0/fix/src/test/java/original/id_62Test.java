package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Derivative.
*/
class DerivativeTest {
    @Test
    void testDerivative() {
        List<Integer> xs = new ArrayList<>(List.of(3, 1, 2, 4, 5));
        List<Object> result = Derivative.derivative(xs);
        assertEquals(List.of(1, 4, 12, 20), result);
    }
    
    @Test
        void testNothing(){
            Derivative s = new Derivative();
            }
    @Test
    public void testDerivativeWithFewTerms() {
        List<Integer> input = Arrays.asList(1, 2, 3);
        java.util.List<java.lang.Object> expected = java.util.Arrays.asList(2, 6);
        assertEquals(expected, Derivative.derivative(input));
    }
                                    
}