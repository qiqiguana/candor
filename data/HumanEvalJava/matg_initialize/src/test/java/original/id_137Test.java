package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CompareOne.
*/
class CompareOneTest {
    @Test
    void test_compareOne() {
        Object result = CompareOne.compareOne("5,1", "6");
        assertEquals(result, "6");
    }
}