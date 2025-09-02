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
}