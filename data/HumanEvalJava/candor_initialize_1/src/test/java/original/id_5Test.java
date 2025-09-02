package original;

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
        assertEquals(0, Intersperse.intersperse(numbers, delimiter).size());
    }
    
    @Test
        public void testNothing(){
            Intersperse s = new Intersperse();
            }
    @Test public void testInterspersewithNumbersAndDelimiter() { List<Object> numbers = new ArrayList<>(); numbers.add(1); numbers.add(2); numbers.add(3); int delimiter = 4; List<Object> result = Intersperse.intersperse(numbers, delimiter); assertEquals(5, result.size()); assertEquals(1, result.get(0)); assertEquals(delimiter, result.get(1)); assertEquals(2, result.get(2)); assertEquals(delimiter, result.get(3)); assertEquals(3, result.get(4));}
                                    
}