package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Modp.
*/
class ModpTest {
    @Test
    void test_modp_0_n() {
        int n = 0;
        int p = 101;
        int expectedResult = 1;
        int actualResult = Modp.modp(n, p);
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            Modp s = new Modp();
            }
    @Test
    public void specificFunctionalityTest_LargeInput() {
        int n = 1101;
        int p = 101;
        int expectedResult = 2;
        int result = Modp.modp(n, p);
        assertEquals(expectedResult % p, result);
    }
    @Test
    public void positiveTest_PowerOf2_EdgeCase() {
        int n = 30;
        int p = 5;
        int expectedResult = 4;
        int result = original.Modp.modp(n, p);
        org.junit.jupiter.api.Assertions.assertEquals(expectedResult, result);
    }
    @Test
    public void edgeCaseTest_ModulusByPrimeNumber_1() {
        int n = 3;
        int p = 11;
        int expectedResult = 8;
        int result = Modp.modp(n, p);
        assertEquals(expectedResult, result);
    }
                                    
}