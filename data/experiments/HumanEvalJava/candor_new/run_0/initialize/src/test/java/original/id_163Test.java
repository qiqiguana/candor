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
    void testGenerateIntegersInRange() {
        List<Object> result = GenerateIntegers.generateIntegers(2, 8);
        assertEquals(List.of(2, 4, 6, 8), result);
    }
}