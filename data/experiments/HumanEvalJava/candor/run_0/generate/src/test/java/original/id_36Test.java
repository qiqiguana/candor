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
    
    @Test
        public void testNothing(){
            FizzBuzz s = new FizzBuzz();
            }
    @Test
    public void testFizzBuzz_SmallInput() {
        int n = 50;
        int expectedResult = 0;
        int actualResult = FizzBuzz.fizzBuzz(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testFizzBuzz_MediumInput() {
        int n = 78;
        int expectedResult = 2;
        int actualResult = FizzBuzz.fizzBuzz(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testFizzBuzz_LargeInput() {
        int n = 10000;
        int expectedResult = 639;
        int actualResult = FizzBuzz.fizzBuzz(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testFizzBuzz_ZeroInput() {
        int n = 0;
        int expectedResult = 0;
        int actualResult = FizzBuzz.fizzBuzz(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testFizzBuzz_NegativeInput() {
        int n = -1;
        int expectedResult = 0;
        int actualResult = FizzBuzz.fizzBuzz(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testFizzBuzz_NoSevens() {
        int n = 100;
        int expectedResult = 3;
        int actualResult = FizzBuzz.fizzBuzz(n);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testFizzBuzz_MultipleOfBoth() {
        int n = 143;
        int expectedResult = 4;
        int actualResult = FizzBuzz.fizzBuzz(n);
        assertEquals(expectedResult, actualResult);
    }
                                    
}