package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of BelowZero.
*/
class BelowZeroTest {
    @Test
    void testBelowZero_ReturnsTrue_WhenBalanceFallsBelowZero() {
        List<Object> operations = List.of(1, 2, -4, 5);
        assertTrue(BelowZero.belowZero(operations));
    }
}