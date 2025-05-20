package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetPositive.
*/
class GetPositiveTest {
    @Test
    void testGetPositiveListWithMultipleElements() {
        List<Object> list = new ArrayList<>();
        list.add(-1);
        list.add(2);
        list.add(-4);
        list.add(5);
        list.add(6);
        List<Object> result = GetPositive.getPositive(list);
        assertEquals(result.size(), 3);
    }
}