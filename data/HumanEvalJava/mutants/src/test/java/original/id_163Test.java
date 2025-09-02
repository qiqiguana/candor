package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GenerateIntegers.
*/
class GenerateIntegersTest {
    @Test
    void testGenerateIntegers_LowerUpperBounds_ReturnsEvenDigitsInRange() {
        List<Object> expected = new ArrayList<>(List.of(2, 4, 6, 8));
        assertEquals(expected, GenerateIntegers.generateIntegers(2, 10));
    }
    
    @Test
        public void testNothing(){
            GenerateIntegers s = new GenerateIntegers();
            }
    @Test
    public void testEvenDigitsBetween2And8() {
        List<Object> result = GenerateIntegers.generateIntegers(2, 8);
        assertEquals(List.of(2, 4, 6, 8), result);
    }
    @Test
    public void testEvenDigitsBetween8And2() {
        List<Object> result = GenerateIntegers.generateIntegers(8, 2);
        assertEquals(List.of(2, 4, 6, 8), result);
    }
    @Test
    public void testEvenDigitsBetween10And14() {
        List<Object> result = GenerateIntegers.generateIntegers(10, 14);
        assertEquals(List.of(), result);
    }
    @Test
    public void testInvalidInputNegativeNumbers() {
        List<Object> result = GenerateIntegers.generateIntegers(-2, -8);
        assertEquals(List.of(), result);
    }
    @Test
    public void testEdgeCaseEqualNumbers() {
        List<Object> result = GenerateIntegers.generateIntegers(4, 4);
        assertEquals(List.of(4), result);
    }
    @Test
    public void testValidInputIntegerValues1() {
        List<Object> result = GenerateIntegers.generateIntegers(2, 8);
        assertEquals(List.of(2, 4, 6, 8), result);
    }
                                    
}