package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FizzBuzz.
*/
class FizzBuzzTest {
    @Test
    void testFizzBuzz_DivisibleBy11And13_CountsSevensCorrectly() {
        int result = FizzBuzz.fizzBuzz(100);
        assertEquals(3, result);
    }
}