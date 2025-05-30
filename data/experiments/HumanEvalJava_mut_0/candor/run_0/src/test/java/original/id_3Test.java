package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of BelowZero.
*/
class BelowZeroTest {
    @Test
    void testBelowZero() {
        List<Object> operations = new ArrayList<>();
        operations.add(10);
        operations.add(-20.0);
        assertFalse(BelowZero.belowZero(operations));
    }
}