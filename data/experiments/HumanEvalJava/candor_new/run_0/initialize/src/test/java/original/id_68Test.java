package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Pluck.
*/
class PluckTest {

    @Test
    void testpluck_Smallest_Even_Value() {
        List<Object> input = new ArrayList<>();
        input.add(4);
        input.add(2);
        input.add(3);
        List<Object> expectedOutput = new ArrayList<>();
        expectedOutput.add(2);
        expectedOutput.add(1);

        assertEquals(expectedOutput, Pluck.pluck(input));
    }
}