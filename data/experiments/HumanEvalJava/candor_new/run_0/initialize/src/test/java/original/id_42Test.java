package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of IncrList.
*/
class IncrListTest {

    @Test
    void testIncrList_withIntegerValues_ReturnsIncrementedValues() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);

        List<Object> result = IncrList.incrList(input);
        assertEquals(3, result.size());
        assertEquals(2, result.get(0));
    }
}