package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Simplify.
*/
class SimplifyTest {
    @Test
    void test_simplify_returns_true_for_valid_input() {
        String x = "1/5";
        String n = "5/1";
        Boolean result = Simplify.simplify(x, n);
        assertTrue(result);
    }
}
