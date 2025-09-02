package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of F.
*/
class FTest {
    @Test
    void testFForEvenNumber() {
        List<Integer> result = F.f(4);
        assertEquals(24, (int) result.get(3));
    }
    
    @Test
        public void testNothing(){
            F s = new F();
            }
                                    
}