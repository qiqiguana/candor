package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RollingMax.
*/
class RollingMaxTest {
    @Test
    void testRollingMax_withIncreasingNumbers_ReturnsCorrectList() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        assertEquals(expected, RollingMax.rollingMax(numbers));
    }
}