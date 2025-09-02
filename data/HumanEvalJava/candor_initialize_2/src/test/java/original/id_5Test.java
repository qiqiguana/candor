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
    void testInterspersedListHasCorrectSize() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        int delimiter = 4;
        List<Object> result = Intersperse.intersperse(numbers, delimiter);
        assertEquals(numbers.size() * 2 - 1, result.size());
    }
    
    @Test
        public void testNothing(){
            Intersperse s = new Intersperse();
            }
                                    
}