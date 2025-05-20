package original;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeFib.
*/
class PrimeFibTest {

@Test
void testPrimeFib() {
    assertEquals(2, PrimeFib.primeFib(1));
}
@Test
public void testPrimeFibForHigherN() {
    assertEquals(233, PrimeFib.primeFib(6));
    assertEquals(1597, PrimeFib.primeFib(7));
    assertEquals(28657, PrimeFib.primeFib(8));
    assertEquals(514229, PrimeFib.primeFib(9));
    assertEquals(433494437, PrimeFib.primeFib(10));
}
@org.junit.jupiter.api.Test
public void testPrimeNumbersFunctionality() {
    int[] input = { 1, 2, 3, 4, 5 };
    int[] expectedResults = { 2, 3, 5, 13, 89 };
    for (int i = 0; i < input.length; i++) {
        org.junit.jupiter.api.Assertions.assertEquals(expectedResults[i], original.PrimeFib.primeFib(input[i]));
    }
}

}