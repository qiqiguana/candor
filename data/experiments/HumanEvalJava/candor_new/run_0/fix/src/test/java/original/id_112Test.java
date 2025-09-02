package original;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ReverseDelete.
*/
class ReverseDeleteTest {
    @Test
    void test_reverse_delete_single_character() {
        String s = "a";
        String c = "a";
        List<Object> expected = Arrays.asList("", true);
        assertEquals(expected, ReverseDelete.reverseDelete(s, c));
    }
    
    @Test
        public void testNothing(){
            ReverseDelete s = new ReverseDelete();
            }
    @Test
    public void testReverseDeleteNoCharactersToRemove2() {
        List<Object> result = ReverseDelete.reverseDelete("hello", "");
        assertEquals(Arrays.asList("hello", false), result);
    }
    @Test
    public void testPalindromeEvenLength() {
        List<Object> result = ReverseDelete.reverseDelete("abcdedcba", "ab");
        assertTrue((Boolean) result.get(1));
        assertEquals("cdedc", result.get(0));
    }
                                    
}