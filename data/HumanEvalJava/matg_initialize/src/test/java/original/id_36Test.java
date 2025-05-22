package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FizzBuzz.
*/
class FizzBuzzTest {

    @Test
    void testFizzBuzz() {
        assertEquals(0, FizzBuzz.fizzBuzz(50));
    }
}