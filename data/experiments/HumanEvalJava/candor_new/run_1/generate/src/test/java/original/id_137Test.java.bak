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
    
    @Test
        public void testNothing(){
            CompareOne s = new CompareOne();
            }
    @Test
    public void CompareOne_CompareStringsWithComma_ReturnsCorrectResult() {
        Object result = CompareOne.compareOne("1,2", "3,4");
        assertEquals("3,4", result);
    }
    @Test
    public void CompareOne_CompareEqualNumbers_ReturnsNull() {
        Object result = CompareOne.compareOne(1, 1);
        assertNull(result);
    }
                                    
}