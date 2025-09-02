package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Derivative.
*/
class DerivativeTest {
    @Test
    void testDerivative_SingleElement_ReturnEmptyList() {
        List<Integer> input = new ArrayList<>();
        input.add(1);
        List<Object> result = Derivative.derivative(input);
        assertTrue(result.isEmpty());
    }
    
    @Test
        public void testNothing(){
            Derivative s = new Derivative();
            }
    @Test
    public void testDerivative_MultiElementList() {
        List<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(4);
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(6);
        expected.add(12);
        List<Object> actual = Derivative.derivative(input);
        assertEquals(expected, actual);
    }
                                    
}