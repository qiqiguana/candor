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
    void testReverseDelete() {
        String s = "abcde";
        String c = "ae";
        List<Object> result = ReverseDelete.reverseDelete(s, c);
        assertTrue(result.get(1) instanceof Boolean);
    }
    
    @Test
     void testNothing(){
         ReverseDelete s = new ReverseDelete();
         }
    @Test
    public void testReverseDeleteHappyPath() {
        List<Object> result = original.ReverseDelete.reverseDelete("abcde", "ae");
        assertEquals(Arrays.asList("bcd", false), result);
    }
    @Test
    public void testReverseDeleteSingleCharacterDeletion() {
        List<Object> result = original.ReverseDelete.reverseDelete("abcdef", "b");
        assertEquals(Arrays.asList("acdef", false), result);
    }
    @Test
    public void testReverseDeletePalindromeString() {
        List<Object> result = original.ReverseDelete.reverseDelete("abcdedcba", "ab");
        assertEquals(Arrays.asList("cdedc", true), result);
    }
    @Test
    public void testReverseDeleteEmptyDeletionString() {
        List<Object> result = original.ReverseDelete.reverseDelete("abcdedcba", "");
        assertEquals(Arrays.asList("abcdedcba", true), result);
    }
    @Test
    public void testReverseDeleteNullPointerException() {
        assertThrows(NullPointerException.class, () -> original.ReverseDelete.reverseDelete(null, ""));
    }
                                  
}