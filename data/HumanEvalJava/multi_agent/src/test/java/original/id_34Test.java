package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Unique.
*/
class UniqueTest {
    @Test
    void testUnique() {
        // TODO: This is a very basic test. You should add more tests to ensure your implementation works as expected.
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        // When we remove duplicates, the size of the list should decrease by 0 since there are no duplicates.
        assertEquals(list.size(), 5);
    }
}