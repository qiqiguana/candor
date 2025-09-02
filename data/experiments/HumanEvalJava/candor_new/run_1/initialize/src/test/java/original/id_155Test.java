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
    void testEvenOddCount() {
        List<Integer> result = EvenOddCount.evenOddCount(123);
        assertEquals(Arrays.asList(1, 2), result);
    }
}