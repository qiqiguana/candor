package original;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsNested.
*/
class IsNestedTest {
    @Test
    void testIsNested1() {
        Boolean result = IsNested.isNested("[[[]]]");
        assertFalse(result);
    }
}
