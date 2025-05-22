package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CanArrange.
*/
class CanArrangeTest {
    @Test
    void testCanArrange_Return3_WhenArrayIsSortedInAscendingOrderWithOneElementNotFollowingTheSequence() {
        List<Object> inputList = new ArrayList<>();
        inputList.add(1);
        inputList.add(2);
        inputList.add(4);
        inputList.add(3);
        inputList.add(5);
        int expectedIndex = 3;
        int actualIndex = CanArrange.canArrange(inputList);
        assertEquals(expectedIndex, actualIndex);
    }
}