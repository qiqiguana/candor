package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AnyInt.
*/
class AnyIntTest {
    @Test
    void testAnyInt_True() {
        assertTrue(AnyInt.anyInt(5, 2, 7));
    }
    
    @Test
        public void testNothing(){
            AnyInt s = new AnyInt();
            }
    @Test
    public void testAnyInt_SumOfTwo_Integers_1() {
        assertEquals(true, AnyInt.anyInt(5, 2, 3));
    }
    @Test
    public void testAnyInt_NotSumOfTwo_Integers() {
        assertEquals(false, AnyInt.anyInt(2, 3, 4));
    }
    @Test
    public void testAnyInt_SumOfTwo_NonIntegers() {
        assertEquals(false, AnyInt.anyInt(1.5, 5, 3.5));
    }
    @Test
    public void anyInt_45_ReturnsTrueWhenYEqualsXPlusZ() {
        assertEquals(true, AnyInt.anyInt(2, 3, 1));
    }
                                    
}