package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CompareOne.
*/
class CompareOneTest {
    @Test
    void testCompareOne_DifferentDataType_ReturnsNull() {
        Object result = CompareOne.compareOne("1", 1);
        assertNull(result);
    }
    
    @Test
        void testNothing(){
            CompareOne s = new CompareOne();
            }
    @Test
    public void TestCompareOneWithTwoIntegers1() {
        Object result = CompareOne.compareOne(1, 2);
        assertEquals(2, result);
    }
    @Test
    public void TestCompareOneWithTwoFloats2() {
        Object result = CompareOne.compareOne(1.5f, 2.7f);
        assertEquals(Float.valueOf(2.7f), Float.valueOf(result.toString()));
    }
    @Test
    public void TestCompareOneWithIntegerAndFloat3() {
        Object result = CompareOne.compareOne(1, 2.5f);
        assertEquals(Float.valueOf("2.5"), Float.valueOf(result.toString()));
    }
    @Test
    public void TestCompareOneWithTwoStringsRepresentingNumbers4() {
        Object result = CompareOne.compareOne("5,1", "6");
        assertEquals("6", result);
    }
    @Test
    public void TestCompareOneWithStringAndInteger5() {
        Object result = CompareOne.compareOne("1", 1);
        assertNull(result);
    }
    @Test
    public void TestCompareOneWithTwoEqualIntegers6() {
        Object result = CompareOne.compareOne(1, 1);
        assertNull(result);
    }
                                    
}