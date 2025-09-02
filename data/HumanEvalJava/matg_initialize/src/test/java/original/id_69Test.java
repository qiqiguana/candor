package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Search.
*/
class SearchTest {
    @Test
    void testSearch() {
        List<Integer> lst = new ArrayList<>();
        lst.add(5);
        lst.add(7);
        lst.add(7);
        lst.add(8);
        lst.add(9);
        assertEquals(Search.search(lst), -1);
    }
}