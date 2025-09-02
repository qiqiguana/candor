package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AnyInt.
*/
class AnyIntTest {
    @Test
    void testAnyIntReturnsTrueIfOneOfTheNumbersIsEqualSumOtherTwoAndAllAreIntegers() {
        assertTrue(AnyInt.anyInt(5, 2, 7));
    }
    
    @Test
        public void testNothing(){
            AnyInt s = new AnyInt();
            }
    @Test public void testAnyInt_DifferentIntegerCombinations_1() { assertTrue(AnyInt.anyInt(2, 3, -1)); }
    @Test
    public void testAnyInt_NonIntegerInstances() {
        assertFalse(AnyInt.anyInt(3.0, 4, 7));
    }
    @Test
    public void testAnyInt_EqualNumbers_Fixed_1() { Boolean result = AnyInt.anyInt(2, 3, 1); assertTrue(result); }
    @Test
    public void testAnyInt_ThreeEqualIntegers() {
    	assertFalse(AnyInt.anyInt(2, 2, 2));
    }
    @Test
    public void testAnyInt_OneIntegerAndTwoNonIntegerNumbers() {
    	assertFalse(AnyInt.anyInt(2, 2.5, 3.5));
    }
    @Test
    public void testAnyInt_withNullValues_Fixed_2() {
        assertThrows(NullPointerException.class, () -> AnyInt.anyInt(1, 2, (Number) null));
    }
                                    
}