package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FizzBuzz.
*/
class FizzBuzzTest {
    @Test
    void test_fizzBuzz_divisible_by_11_or_13_has_seven() {
        assertEquals(192, FizzBuzz.fizzBuzz(4000));
    }
}