package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GenerateIntegers.
*/
class GenerateIntegersTest {
    @Test
    void testGenerateIntegers_LowerUpperBounds_ReturnsEvenDigitsInRange() {
        List<Object> expected = new ArrayList<>(List.of(2, 4, 6, 8));
        assertEquals(expected, GenerateIntegers.generateIntegers(2, 10));
    }
}