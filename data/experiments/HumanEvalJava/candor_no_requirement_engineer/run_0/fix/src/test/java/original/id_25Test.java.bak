package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Factorize.
*/
class FactorizeTest {
    @Test
    void factorize() {
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(2);
        expected.add(2);
        assertEquals(expected, Factorize.factorize(4));
    }
    
    @Test
        void testNothing(){
            Factorize s = new Factorize();
            }
    @Test
    public void PositiveTest_SmallPrimeFactor() {
        List<Integer> result = Factorize.factorize(2);
        assertEquals(List.of(2), result);
    }
    @Test
    public void PositiveTest_LargeCompositeNumber() {
        List<Integer> result = Factorize.factorize(3249);
        assertEquals(List.of(3, 3, 19, 19), result);
    }
    @Test
    public void EdgeCaseTest_SinglePrimeFactor() {
        List<Integer> result = Factorize.factorize(7);
        assertEquals(List.of(7), result);
    }
    @Test
    public void SpecificFunctionalityTest_PrimeSquare() {
        List<Integer> result = Factorize.factorize(25);
        assertEquals(List.of(5, 5), result);
    }
    @Test
    public void EdgeCaseTest_SinglePrimeFactor2() {
        List<Integer> result = Factorize.factorize(11);
        assertEquals(List.of(11), result);
    }
                                    
}