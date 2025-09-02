package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
* Test class of DoubleTheDifference.
*/
class DoubleTheDifferenceTest {

    @Test
    void test_doubleTheDifference_withOddNumbers() {
        List<Object> list = List.of(1, 3, 5);
        int expected = 35;
        int actual = DoubleTheDifference.doubleTheDifference(list);
        assertEquals(expected, actual);
    }
}
