package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of DoubleTheDifference.
*/
class DoubleTheDifferenceTest {
    @Test
    void testDoubleTheDifference_SingleOddNumber_ReturnSquareOfNumber() {
        List<Object> lst = new ArrayList<>();
        lst.add(5);
        int result = DoubleTheDifference.doubleTheDifference(lst);
        assertEquals(25, result);
    }
}