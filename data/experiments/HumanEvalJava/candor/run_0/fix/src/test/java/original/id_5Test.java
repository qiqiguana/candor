package original;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Intersperse.
*/
class IntersperseTest {

    @Test
    void testInterspersewithEmptyList() {
        List<Object> numbers = new ArrayList<>();
        int delimiter = 4;
        List<Object> result = Intersperse.intersperse(numbers, delimiter);
        assertEquals(0, result.size());
    }
    
    @Test
        void testNothing(){
            Intersperse s = new Intersperse();
            }
    @Test
    public void testEmptyList() {
        List<Object> numbers = new ArrayList<>();
        int delimiter = 4;
        assertEquals(new ArrayList<>(), Intersperse.intersperse(numbers, delimiter));
    }
    @Test
    public void testSingleElementList() {
        List<java.lang.Object> numbers = java.util.Arrays.asList(1);
        int delimiter = 4;
        assertEquals(java.util.Arrays.asList(1), original.Intersperse.intersperse(numbers, delimiter));
    }
    @Test
    public void testNegativeDelimiter() {
    	List<Object> numbers = Arrays.asList(1, 2, 3);
    	int delimiter = -4;
    	assertEquals(Arrays.asList(1, -4, 2, -4, 3), Intersperse.intersperse(numbers, delimiter));
    }
    @Test
    public void testZeroDelimiterCorrectly() {
        List<Object> numbers = Arrays.asList(1, 2, 3);
        int delimiter = 0;
        assertEquals(Arrays.asList(1, 0, 2, 0, 3), Intersperse.intersperse(numbers, delimiter));
    }
                                    
}