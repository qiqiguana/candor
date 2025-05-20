package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of BelowZero.
*/
class BelowZeroTest {
    @Test
    void testBelowZero_withListHavingNegativeBalance_ReturnsTrue() {
        List<Object> operations = new ArrayList<>();
        operations.add(1);
        operations.add(-2);
        assertTrue(BelowZero.belowZero(operations));
    }
}