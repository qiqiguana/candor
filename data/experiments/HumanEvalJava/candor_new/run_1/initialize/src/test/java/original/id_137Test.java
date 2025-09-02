package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CompareOne.
*/
class CompareOneTest {
    @Test
    void testCompareOne_DifferentTypes_ReturnsLargerValue() {
        Object result = CompareOne.compareOne("1", 2);
        assertEquals(2, result);
    }
}