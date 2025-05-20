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
        List<Integer> actual = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            actual.add(i);
        }
        assertEquals(new ArrayList<>(actual), actual);
    }
}