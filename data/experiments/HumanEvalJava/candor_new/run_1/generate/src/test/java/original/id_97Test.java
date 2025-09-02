package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Multiply.
*/
class MultiplyTest {

@Test
public void testMultiply(){
    assertNotEquals(0, 1 * 2);
}

@Test
    public void testNothing(){
        Multiply s = new Multiply();
        }
@Test
public void TestMultiplyPositiveNumbers() {
    int result = Multiply.multiply(17, 27);
    assertEquals(49, result);
}
@Test
public void TestMultiplyNegativeNumbers() {
    int result = Multiply.multiply(14, -15);
    assertEquals(20, result);
}
@Test
public void TestMultiplyEdgeCaseNegative() {
    int result = Multiply.multiply(-19, -28);
    assertEquals(72, result);
}
                                
}