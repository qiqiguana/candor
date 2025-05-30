package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of IsSorted.
*/
class IsSortedTest {

    @Test
    void testIsSorted() {
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertTrue(IsSorted.isSorted(list));
    }
    
    @Test
        public void testNothing(){
            IsSorted s = new IsSorted();
            }
    @Test
    public void testIsSortedWithEmptyList() {
        List<Object> input = new ArrayList<>();
        assertTrue(IsSorted.isSorted(input));
    }
                                    
}