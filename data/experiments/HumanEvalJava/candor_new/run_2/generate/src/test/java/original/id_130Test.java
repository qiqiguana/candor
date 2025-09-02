package original;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
    void testTriZero() {
        List<Number> result = Tri.tri(0);
        assertEquals(Arrays.asList(1), result);
    }
    
    @Test
        public void testNothing(){
            Tri s = new Tri();
            }
    @Test
    public void testTriMethodHappyPathEvenI() {
    	List<Number> result = Tri.tri(2);
    	assertEquals(result.get(2), 2.0);
    }
    @Test
    public void testTriForOddN() {
        List<Number> result = Tri.tri(3);
        Number[] expectedResult = {1, 3, 2.0, 8.0};
        assertArrayEquals(expectedResult, result.toArray());
    }
                                    
}