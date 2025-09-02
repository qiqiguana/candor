package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Tri.
*/
class TriTest {
    @Test
    void testTri() {
        List<Number> expected = Arrays.asList(1, 3, 2.0, 8.0, 3.0);
        assertEquals(expected, Tri.tri(4));
    }
    
    @Test
        public void testNothing(){
            Tri s = new Tri();
            }
    @Test
    public void tri_n_is_zero() {
    	List<Number> result = Tri.tri(0);
    	List<Number> expected = Arrays.asList(1);
    	assertEquals(expected, result);
    }
                                    
}