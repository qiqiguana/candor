package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsMultiplyPrime.
*/
class IsMultiplyPrimeTest {
    @Test
    void testIsMultiplyPrime() {
        assertTrue(IsMultiplyPrime.isMultiplyPrime(30));
    }
    
    @Test
        public void testNothing(){
            IsMultiplyPrime s = new IsMultiplyPrime();
            }
    @Test
    public void TestIsMultiplyPrimeClass() {
        Class<?> clazz = original.IsMultiplyPrime.class;
        assertEquals("class original.IsMultiplyPrime", clazz.toString());
    }
    @Test
    public void Test_Multiply_Prime_Number_With_Three_Distinct_Prime_Factors() {
        assertTrue(IsMultiplyPrime.isMultiplyPrime(2*3*5));
    }
    @Test
    public void Test_Multiply_Prime_Number_With_Three_Identical_Prime_Factors() {
        assertTrue(IsMultiplyPrime.isMultiplyPrime(2*2*2));
    }
    @Test
    public void Test_Multiply_Prime_Number_With_Less_Than_Three_Prime_Factors() {
        assertFalse(IsMultiplyPrime.isMultiplyPrime(2*3));
    }
                                    
}