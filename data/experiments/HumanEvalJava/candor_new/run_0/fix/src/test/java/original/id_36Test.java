package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FizzBuzz.
*/
class FizzBuzzTest {
    @Test
    void testFizzBuzz_WhenNIs50_Returns0() {
        assertEquals(0, FizzBuzz.fizzBuzz(50));
    }
    
    @Test
        public void testNothing(){
            FizzBuzz s = new FizzBuzz();
            }
    @Test
    public void testFizzBuzzCountIncrement_77_fixed() {
        int result = FizzBuzz.fizzBuzz(78);
        assertEquals(2, result);
    }
                                    
}