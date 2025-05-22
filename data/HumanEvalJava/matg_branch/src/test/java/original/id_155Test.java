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
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
    }
}