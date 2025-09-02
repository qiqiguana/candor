package original;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of EvenOddCount.
*/
class EvenOddCountTest {
    @Test
    void testEvenOddCount_Zero() {
        List<Integer> result = EvenOddCount.evenOddCount(0);
        assertEquals(Arrays.asList(1, 0), result);
    }
}