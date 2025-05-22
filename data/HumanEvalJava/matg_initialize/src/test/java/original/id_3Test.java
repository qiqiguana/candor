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
    void belowZero_DetectsNegativeBalance() {
        List<Object> operations = new ArrayList<>();
        operations.add(1);
        operations.add(2);
        operations.add(-4);
        assertTrue(BelowZero.belowZero(operations));
    }
}