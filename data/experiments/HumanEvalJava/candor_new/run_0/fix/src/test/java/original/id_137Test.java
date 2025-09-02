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
    
    @Test
        public void testNothing(){
            CompareOne s = new CompareOne();
            }
    @Test
    public void test_compareOne_string_input_different_values() {
        Object result = CompareOne.compareOne("5,1", "6");
        assertEquals("6", result);
    }
    @Test
    public void test_compareOne_equal_float_values() {
        Object result = CompareOne.compareOne(2.5f, 2.5f);
        assertNull(result);
    }
    @Test
    public void compareOne_float_greater_than_int() {
        Float a = new Float(2.5);
        Integer b = new Integer(1);
        assertTrue(a.equals(CompareOne.compareOne(a, b)));
    }
                                    
}