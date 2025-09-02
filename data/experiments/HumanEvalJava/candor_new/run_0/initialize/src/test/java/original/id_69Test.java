package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Search.
*/
class SearchTest {

    @Test
    void test_Search_singleElementList() {
        List<Integer> lst = List.of(5);
        assertEquals(-1, Search.search(lst));
    }
}