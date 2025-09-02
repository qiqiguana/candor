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
    void testDerivative_NonConstantPolynomial_ReturnsCorrectDerivative() {
        List<Integer> input = new ArrayList<>(List.of(3, 2, 1));
        List<Object> expectedOutput = new ArrayList<>(List.of(2, 2));
        assertEquals(expectedOutput, Derivative.derivative(input));
    }
}