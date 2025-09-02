package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Search.
*/
class SearchTest {
    @Test
    public void test_search_return_correct_value_when_list_contains_multiple_elements() {
        List<Integer> list = java.util.Arrays.asList(1, 2, 3);
        int result = original.Search.search(list);
        assertEquals(1, result);
    }
}