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
    void testGenerateIntegers() {
        List<Object> result = GenerateIntegers.generateIntegers(2, 10);
        assertEquals(result.toString(), "[2, 4, 6, 8]");
    }
}