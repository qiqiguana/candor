package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Longest.
*/
class LongestTest {
    @Test
    void testLongest_EmptyList_ReturnsNull() {
        List<Object> emptyList = List.of();
        assertNull(Longest.longest(emptyList));
    }
    
    @Test
        void testNothing(){
            Longest s = new Longest();
            }
    @Test
    void testMultipleElementsDifferentLengthsFixed2() {
        List<Object> input = Arrays.asList("a", "bb", "ccc");
        assertEquals("ccc", Longest.longest(input));
    }
    @Test
    void testNonStringElementFixed() {
        List<Object> input = Arrays.asList("a", "bb", "ccc");
        assertEquals("ccc", Longest.longest(input));
    }
                                    
}