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
    void testFactorize() {
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(5);
        expected.add(7);
        assertEquals(expected, Factorize.factorize(70));
    }
    
    @Test
        public void testNothing(){
            Factorize s = new Factorize();
            }
                                    
}