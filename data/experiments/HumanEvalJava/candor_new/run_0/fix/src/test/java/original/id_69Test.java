package original;

import java.util.Arrays;
import java.util.ArrayList;
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
    
    @Test
        public void testNothing(){
            Search s = new Search();
            }
    @Test
    public void testCaseForMaxValueHandlesLargeInput1() {
        List<Integer> lst = new ArrayList<>(Arrays.asList(5, 4, 10, 2, 1, 1, 10, 3, 6, 1, 8));
        int result = Search.search(lst);
        assertEquals(1, result);
    }
                                    
}