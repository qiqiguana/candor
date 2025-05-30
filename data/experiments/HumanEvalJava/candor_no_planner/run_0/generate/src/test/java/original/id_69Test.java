package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Search.
*/
class SearchTest {
    @Test
    public void testSearch_ExpectedValue() {
        List<Integer> list = List.of(1, 2, 2, 3, 3, 3);
        assertEquals(3, Search.search(list));
    }
    
    @Test
        public void testNothing(){
            Search s = new Search();
            }
                                    
}