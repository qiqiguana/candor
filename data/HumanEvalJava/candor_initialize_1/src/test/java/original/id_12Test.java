package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Longest.
*/
class LongestTest {

    @Test
    void testLongest_EmptyList_ReturnsNull() {
        List<Object> strings = List.of();
        assertNull(Longest.longest(strings));
    }
    
    @Test
        public void testNothing(){
            Longest s = new Longest();
            }
    @Test
    public void testLongestStringWithMultipleLongestStrings() {
        List<Object> strings = Arrays.asList("x", "yyy", "zzzz", "www", "kkkk", "abc");
        String result = Longest.longest(strings);
        assertTrue(Arrays.asList("kkkk", "zzzz").contains(result));
    }
                                    

}