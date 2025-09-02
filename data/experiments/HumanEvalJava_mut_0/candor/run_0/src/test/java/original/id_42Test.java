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
    void testIncrList() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        List<Object> result = IncrList.incrList(input);
        assertEquals(result.size(), 0);
    }
}