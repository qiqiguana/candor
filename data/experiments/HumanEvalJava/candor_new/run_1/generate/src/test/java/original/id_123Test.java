package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetOddCollatz.
*/
class GetOddCollatzTest {
    @Test
    public void testGetOddCollatz() {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(5);
        Collections.sort(result);
        assertEquals(result, GetOddCollatz.getOddCollatz(5));
    }
    
    @Test
        public void testNothing(){
            GetOddCollatz s = new GetOddCollatz();
            }
                                    
}