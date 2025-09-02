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
    void testInterspersedListWithDelimiter() {
        List<Object> numbers = new ArrayList<>(List.of(1, 2, 3));
        int delimiter = 4;
        List<Object> expected = new ArrayList<>(List.of(1, 4, 2, 4, 3));
        assertEquals(expected, Intersperse.intersperse(numbers, delimiter));
    }
    
    @Test
        public void testNothing(){
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
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        int delimiter = 2;
        assertEquals(numbers, Intersperse.intersperse(numbers, delimiter));
    }
    @Test
    public void testMultipleElementsList() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        int delimiter = 4;
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(delimiter);
        expected.add(2);
        expected.add(delimiter);
        expected.add(3);
        assertEquals(expected, Intersperse.intersperse(numbers, delimiter));
    }
    @Test
    public void testSameDelimiterAndNumber() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(2);
        numbers.add(2);
        numbers.add(2);
        int delimiter = 2;
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(delimiter);
        expected.add(2);
        expected.add(delimiter);
        expected.add(2);
        assertEquals(expected, Intersperse.intersperse(numbers, delimiter));
    }
    @Test
    public void testNullInputList() {
        List<Object> numbers = null;
        int delimiter = 4;
        assertThrows(NullPointerException.class, () -> Intersperse.intersperse(numbers, delimiter));
    }
                                    
}