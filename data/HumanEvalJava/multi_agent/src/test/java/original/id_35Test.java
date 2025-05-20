package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MaxElement.
*/
class MaxElementTest {
    @Test
    void testMaxElement_ReturnsMaximumValueInList() {
        List<Integer> numbers = List.of(1, 2, 3);
        int expectedResult = 3;
        int actualResult = MaxElement.maxElement(numbers);
        assertEquals(expectedResult, actualResult);
    }
}