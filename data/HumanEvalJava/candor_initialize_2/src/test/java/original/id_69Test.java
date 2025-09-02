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
    void test_search() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        assertEquals(2, Search.search(list));
    }
    
    @Test
        public void testNothing(){
            Search s = new Search();
            }
                                    
}