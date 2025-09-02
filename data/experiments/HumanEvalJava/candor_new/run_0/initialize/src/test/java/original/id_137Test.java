package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CompareOne.
*/
class CompareOneTest {
    @Test
    void test_compareOne_DifferentDataType_ReturnB() {
        assertEquals("2,3", CompareOne.compareOne(1, "2,3"));
    }
}