package oracle1;

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
        void testNothing(){
            IsMultiplyPrime s = new IsMultiplyPrime();
            }
    @Test
    public void testIsMultiplyPrime_SmallPrimeNumberMultiplication() {
        assertTrue(IsMultiplyPrime.isMultiplyPrime(30));
    }
    @Test
    public void testIsMultiplyPrime_SinglePrimeNumber() {
        assertFalse(IsMultiplyPrime.isMultiplyPrime(5));
    }
    @Test
    public void testIsMultiplyPrime_BoundaryCondition() {
        assertFalse(IsMultiplyPrime.isMultiplyPrime(100));
    }
    @Test
    public void testIsMultiplyPrime_MediumPrimeNumberMultiplication() {
        assertTrue(IsMultiplyPrime.isMultiplyPrime(2*3*17));
    }
    @Test
    public void testIsMultiplyPrime_NonPrimeNumber() {
        assertTrue(IsMultiplyPrime.isMultiplyPrime(4));
    }
    @Test
    public void testIsMultiplyPrime_ZeroInput() {
        assertFalse(IsMultiplyPrime.isMultiplyPrime(0));
    }
                                    
}