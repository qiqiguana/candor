package original;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsNested.
*/
class IsNestedTest {
    @Test
    void testIsNested() {
        assertTrue(IsNested.isNested("[[]]") == true);
    }
}