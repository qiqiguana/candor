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
    void testGetOddCollatz() {
        List<Integer> expected = new ArrayList<>(List.of(1, 5));
        List<Integer> actual = GetOddCollatz.getOddCollatz(5);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            GetOddCollatz s = new GetOddCollatz();
            }
                                    
}