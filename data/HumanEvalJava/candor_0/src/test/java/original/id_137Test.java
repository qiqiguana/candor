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
    @Test
    public void test_compareOne_integerAndFloat() {
        Object result = CompareOne.compareOne(1, 2.5f);
        assertEquals(2.5f, result);
    }
    @Test
    public void test_compareOne_stringNumbers() {
        Object result = CompareOne.compareOne("1", "2");
        assertEquals("2", result);
    }
    @Test
    public void test_compareOne_equalValues() {
        Object result = CompareOne.compareOne(1, 1);
        assertNull(result);
    }
    @Test
    public void test_compareOne_sameNumber() {
        Object result = CompareOne.compareOne("1", 1);
        assertNull(result);
    }
    @Test
    public void test_compareOne_floats() {
        Object result = CompareOne.compareOne(1.5f, 2.5f);
        assertEquals(2.5f, result);
    }
    @Test
    public void compareFloats() {
        assertEquals(2.7f, CompareOne.compareOne(1.5f, 2.7f));
    }
    @Test
    public void compareStrings() {
        assertEquals("2.7", CompareOne.compareOne("1.5", "2.7"));
    }
    @Test
    public void compareMixedTypes() {
        assertEquals(2.7f, CompareOne.compareOne(1, 2.7f));
    }
    @Test
    public void compareEqualValues() {
        assertNull(CompareOne.compareOne(2, 2));
    }
    @Test
    public void edgeCaseCommaAsDecimalSeparator() {
        assertEquals("2.7", CompareOne.compareOne("1,5", "2.7"));
    }
    @Test
    public void compareOne_InvalidInputType1_Fixed() {
        assertThrows(NumberFormatException.class, () -> CompareOne.compareOne(true, 2));
    }
    @Test
    public void testCompareOneFloatsEqual() {
        Object result = CompareOne.compareOne(1.5f, 1.5f);
        assertNull(result);
    }
    @Test
    public void testCompareOneStringsEqual() {
        Object result = CompareOne.compareOne("1", "1");
        assertNull(result);
    }
    @Test
    public void testCompareOneIntegersUnequal() {
        Object result = CompareOne.compareOne(1, 2);
        assertEquals(2, result);
    }
    @Test
    public void testCompareOneFloatsUnequal() {
        Object result = CompareOne.compareOne(1.5f, 2.5f);
        assertEquals(2.5f, result);
    }
    @Test
    public void testCompareOneStringsUnequal() {
        Object result = CompareOne.compareOne("1", "2");
        assertEquals("2", result);
    }
    @Test
    public void testCompareOneIntegersUnequal2() {
        Object result = CompareOne.compareOne(2, 1);
        assertEquals(2, result);
    }
    @Test
    public void testCompareOneFloatsUnequal2() {
        Object result = CompareOne.compareOne(2.5f, 1.5f);
        assertEquals(2.5f, result);
    }
    @Test
    public void testCompareOneStringsUnequal2() {
        Object result = CompareOne.compareOne("2", "1");
        assertEquals("2", result);
    }
                                    
}