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
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        int delimiter = 4;
        List<Object> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(delimiter);
        expectedList.add(2);
        expectedList.add(delimiter);
        expectedList.add(3);
        assertEquals(expectedList, Intersperse.intersperse(numbers, delimiter));
    }
}