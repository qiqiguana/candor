package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String s = "abcdedcba";
        String c = "ab";
        List<Object> result = ReverseDelete.reverseDelete(s, c);
        assertEquals("cdedc", result.get(0));
    }
    @Test
    public void testPalindromeStringWithNoCharactersToDelete() {
        List<Object> result = ReverseDelete.reverseDelete("a", "");
        assertEquals(Arrays.asList("a", true), result);
    }
    @Test
    public void testEmptyStringWithOneCharacterToDelete() {
        List<Object> result = ReverseDelete.reverseDelete("", "a");
        assertEquals(Arrays.asList("", true), result);
    }
    @Test
    public void testPalindromeStringWithAllCharactersToDelete() {
        List<Object> result = ReverseDelete.reverseDelete("a", "a");
        assertEquals(Arrays.asList("", true), result);
    }
    @Test
    public void reverseDelete_test1() {
        List<Object> expected = Arrays.asList("bcd", false);
        List<Object> actual = ReverseDelete.reverseDelete("abcde", "ae");
        assertEquals(expected, actual);
    }
    @Test
    public void reverseDelete_test2() {
        List<Object> expected = Arrays.asList("acdef", false);
        List<Object> actual = ReverseDelete.reverseDelete("abcdef", "b");
        assertEquals(expected, actual);
    }
    @Test
    public void reverseDelete_test3() {
        List<Object> expected = Arrays.asList("cdedc", true);
        List<Object> actual = ReverseDelete.reverseDelete("abcdedcba", "ab");
        assertEquals(expected, actual);
    }
    @Test
    public void reverseDelete_test4() {
        List<Object> expected = Arrays.asList("dik", false);
        List<Object> actual = ReverseDelete.reverseDelete("dwik", "w");
        assertEquals(expected, actual);
    }
    @Test
    public void reverseDelete_test5() {
        List<Object> expected = Arrays.asList("", true);
        List<Object> actual = ReverseDelete.reverseDelete("a", "a");
        assertEquals(expected, actual);
    }
    @Test
    public void reverseDelete_test6() {
        List<Object> expected = Arrays.asList("abcdedcba", true);
        List<Object> actual = ReverseDelete.reverseDelete("abcdedcba", "");
        assertEquals(expected, actual);
    }
    @Test
    public void reverseDelete_test7() {
        List<Object> expected = Arrays.asList("abcdedcba", true);
        List<Object> actual = ReverseDelete.reverseDelete("abcdedcba", "v");
        assertEquals(expected, actual);
    }
    @Test
    public void testClassInitialization() {
    }
    @Test
    public void testEmptyStringAsInput() {
        List<Object> result = ReverseDelete.reverseDelete("", "");
        assertEquals(Arrays.asList("", true), result);
    }
    @Test
    public void testSingleCharacterStringAsInput() {
        List<Object> result = ReverseDelete.reverseDelete("a", "");
        assertEquals(Arrays.asList("a", true), result);
    }
    
    @Test
    public void testReverseDelete2() {
        String s = "abcdef";
        String c = "b";
        List<Object> expected = Arrays.asList("acdef", false);
        assertEquals(expected, ReverseDelete.reverseDelete(s, c));
    }
    
    @Test
    public void testReverseDelete3() {
        String s = "abcdedcba";
        String c = "ab";
        List<Object> expected = Arrays.asList("cdedc", true);
        assertEquals(expected, ReverseDelete.reverseDelete(s, c));
    }
    
    @Test
    public void testReverseDelete4() {
        String s = "dwik";
        String c = "w";
        List<Object> expected = Arrays.asList("dik", false);
        assertEquals(expected, ReverseDelete.reverseDelete(s, c));
    }
    
    @Test
    public void testReverseDelete5() {
        String s = "a";
        String c = "a";
        List<Object> expected = Arrays.asList("", true);
        assertEquals(expected, ReverseDelete.reverseDelete(s, c));
    }
    
    @Test
    public void testReverseDelete6() {
        String s = "";
        String c = "a";
        List<Object> expected = Arrays.asList("", true);
        assertEquals(expected, ReverseDelete.reverseDelete(s, c));
    }
    
    @Test
    public void testReverseDelete8() {
        String s = "a";
        String c = "";
        List<Object> expected = Arrays.asList("a", true);
        assertEquals(expected, ReverseDelete.reverseDelete(s, c));
    }
    @Test
    public void reverseDelete_test2_corrected() {
        List<Object> result = ReverseDelete.reverseDelete("abcdef", "b");
        assertEquals(Arrays.asList("acdef", false), result);
    }
    @Test
    public void reverseDelete_test4_corrected() {
        List<Object> result = ReverseDelete.reverseDelete("dwik", "w");
        assertEquals(Arrays.asList("dik", false), result);
    }
    @Test
    public void testPalindromeStringWithCharactersToDelete() {
        List<Object> result = ReverseDelete.reverseDelete("abcdedcba", "ab");
        assertEquals(Arrays.asList("cdedc", true), result);
    }
    @Test
    public void testNonPalindromeStringWithCharactersToDelete() {
        List<Object> result = ReverseDelete.reverseDelete("abcdef", "bc");
        assertEquals(Arrays.asList("adef", false), result);
    }
}