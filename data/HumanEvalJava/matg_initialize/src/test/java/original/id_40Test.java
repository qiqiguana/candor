package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriplesSumToZero.
*/
class TriplesSumToZeroTest {
    @Test
    void test_triplesSumToZero_EmptyList_False() {
        List<Integer> l = List.of();
        assertFalse(TriplesSumToZero.triplesSumToZero(l));
    }
}