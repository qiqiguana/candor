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
    void testDerivative() {
        List<Integer> input = new ArrayList<>();
        input.add(3);
        input.add(2);
        input.add(1);
        List<Object> expectedOutput = new ArrayList<>();
        expectedOutput.add(2);
        expectedOutput.add(2);
        assertEquals(expectedOutput, Derivative.derivative(input));
    }
}