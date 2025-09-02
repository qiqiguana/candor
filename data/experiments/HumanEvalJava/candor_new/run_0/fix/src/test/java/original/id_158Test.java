package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of FindMax.
*/
class FindMaxTest {
    @Test
    void testFindMax() {
        List<String> words = new ArrayList<>();
        words.add("name");
        words.add("of");
        words.add("string");
        assertEquals("string", FindMax.findMax(words));
    }
    
    @Test
        public void testNothing(){
            FindMax s = new FindMax();
            }
    @Test
    public void testNullInput() {
    assertThrows(NullPointerException.class, () -> FindMax.findMax(null));
    }
    @Test
    public void testEmptyListInput() {
        List<String> emptyList = new ArrayList<>();
        assertNull(FindMax.findMax(emptyList));
    }
                                    
}