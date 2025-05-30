package original;

import original.Multiply;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Multiply.
*/
class MultiplyTest {
    @Test
    void testMultiply_UnitDigitsProduct() {
        assertEquals(16, Multiply.multiply(148, 412));
    }
    
    @Test
        public void testNothing(){
            Multiply s = new Multiply();
            }
    @Test
    public void testMultiplyClass() {
        assertEquals(original.Multiply.class.getName(), "original.Multiply");
    }
    @Test
    public void testMultiplyMethodExists() {
        assertDoesNotThrow(() -> Multiply.class.getMethod("multiply", int.class, int.class));
    }
    @Test
    public void testMultiplyMethodReturnsCorrectResultForPositiveNumbers() {
        assertEquals(16, Multiply.multiply(148, 412));
    }
    @Test
    public void testMultiplyMethodReturnsCorrectResultForNegativeNumbers() {
        assertEquals(20, Multiply.multiply(-14, 15));
    }
    @Test
    public void testMultiplyMethodReturnsZeroForZeroInput() {
        assertEquals(0, Multiply.multiply(0, 1));
    }
    @Test
    public void TestMultiplyWithNegativeNumbers() {
        int a = -14;
        int b = -15;
        int expectedResult = 20;
        int result = Multiply.multiply(a, b);
        assertEquals(expectedResult, result);
    }
                                    
}