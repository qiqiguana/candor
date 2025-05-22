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
    
    @Test
        public void testNothing(){
            CompareOne s = new CompareOne();
            }
    @Test
    public void compareOne_PositiveTest_Integers() {
    	Object result = CompareOne.compareOne(1, 2);
    	assertEquals(result, 2);
    }
    @Test
    public void compareOne_PositiveTest_Floats() {
    	Object result = CompareOne.compareOne(1.5f, 2.5f);
    	assertEquals(result, 2.5f);
    }
    @Test
    public void compareOne_PositiveTest_Strings() {
    	Object result = CompareOne.compareOne("1,5", "2,5");
    	assertEquals(result, "2,5");
    }
    @Test
    public void compareOne_NegativeTest_EqualValues() {
    	Object result = CompareOne.compareOne(1, 1);
    	assertNull(result);
    }
    @Test
    public void compareOne_NegativeTest_InvalidInput() {
    	assertThrows(NumberFormatException.class, () -> CompareOne.compareOne("abc", 2));
    }
    @Test
    public void compareOne_EdgeCaseTest_LargeNumbers() {
    	Object result = CompareOne.compareOne(1000000, 2000000);
    	assertEquals(result, 2000000);
    }
    @Test
    public void compareOne_SpecialCaseTest_CommaSeparatedStrings() {
    	Object result = CompareOne.compareOne("1,000", "2,000");
    	assertEquals(result, "2,000");
    }
                                    
}