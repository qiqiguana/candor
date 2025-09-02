package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Fibfib.
*/
class FibfibTest {
    @Test
    void test_fibfib_2_should_be_equal_to_1() {
        int result = Fibfib.fibfib(2);
        assertEquals(1, result);
    }
}