package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeLength.
*/
class PrimeLengthTest {
    @Test
    void test_primeLength_return_true_for_string_with_prime_length() {
        assertTrue(PrimeLength.primeLength("Hello"));
    }
    
    @Test
        public void testNothing(){
            PrimeLength s = new PrimeLength();
            }
    @Test
    public void testPrimeLength_with_empty_string() {
        assertFalse(PrimeLength.primeLength(""));
    }
    @Test
    public void testPrimeLength_with_single_character_string() {
        assertFalse(PrimeLength.primeLength("a"));
    }
    @Test
    public void testPrimeLength_with_non_prime_length_string() {
        assertFalse(PrimeLength.primeLength("aaaaaa"));
    }
                                    
}