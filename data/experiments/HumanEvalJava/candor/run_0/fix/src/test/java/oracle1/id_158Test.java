package oracle1;

import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of FindMax.
*/
class FindMaxTest {

    @Test
    void test_findMax_should_ReturnWordWithMaximumNumberOfUniqueCharacters() {
        List<String> words = new ArrayList<>();
        words.add("name");
        words.add("of");
        words.add("string");
        String expected = "string";
        String result = FindMax.findMax(words);
        assertEquals(expected, result);
    }
    
    @Test
        void testNothing(){
            FindMax s = new FindMax();
            }
    @Test
    public void testFindMax_SingleWord() {
        List<String> words = Arrays.asList("nation");
        String result = FindMax.findMax(words);
        assertEquals("nation", result);
    }
    @Test
    public void testFindMax_EmptyList() {
        List<String> words = java.util.List.of();
        String result = FindMax.findMax(words);
        assertNull(result);
    }
    @Test
    void testFindMaxWithEmptyList() {
        List<String> words = new ArrayList<>();
        assertNull(FindMax.findMax(words));
    }
    @Test
    void testFindMaxWithSingleElementList() {
        List<String> words = Arrays.asList("hello");
        assertEquals("hello", FindMax.findMax(words));
    }
    @Test
    void testFindMaxWithMultipleElementsAndUniqueCharacters() {
        List<String> words = Arrays.asList("abc", "defg", "hijklmn");
        assertEquals("hijklmn", FindMax.findMax(words));
    }
    @Test
    void testFindMaxWithMultipleElementsAndNoUniqueCharacters() {
        List<String> words = Arrays.asList("aaaa", "bbbb", "cccc");
        assertEquals("aaaa", FindMax.findMax(words));
    }
    @Test
    void testFindMaxWithEmptyString() {
        List<String> words = Arrays.asList("");
        assertEquals("", FindMax.findMax(words));
    }
    @Test
    void testFindMaxWithSingleCharacter() {
        List<String> words = Arrays.asList("a");
        assertEquals("a", FindMax.findMax(words));
    }
    @Test
    void testFindMaxEmptyList() {
        List<String> words = new ArrayList<>();
        String result = FindMax.findMax(words);
        assertNull(result);
    }
    @Test
    void testFindMaxWithNullInputFixed() {
        List<String> words = null;
        assertNull(FindMax.findMax(words));
    }
    @Test
    public void test_findMax_MultipleWordsWithSameMaximumUniqueCharacters() {
        List<String> input = Arrays.asList("name", "enam", "game");
        String expected = "enam";
        assertEquals(expected, FindMax.findMax(input));
    }
    @Test
    public void test_findMax_EmptyList() {
        List<String> input = new ArrayList<>();
        assertNull(FindMax.findMax(input));
    }
    @Test
    public void test_findMax_SingleCharacterWords() {
        List<String> input = Arrays.asList("a", "b", "c");
        String expected = "a";
        assertEquals(expected, FindMax.findMax(input));
    }
    @Test
    public void test_findMax_SameWordRepeated() {
        List<String> input = Arrays.asList("play", "play");
        String expected = "play";
        assertEquals(expected, FindMax.findMax(input));
    }
    @Test
    void testFindMaxWithNullInputFixed2() {
        List<String> words = null;
        assertNull(FindMax.findMax(words));
    }
    @Test
    void testFindMaxWithNullInputFixed1() {
        List<String> words = null;
        assertThrows(NullPointerException.class, () -> FindMax.findMax(words));
    }
    @Test
    void testFindMaxEmptyList2() {
        List<String> words = new ArrayList<>();
        assertNull(FindMax.findMax(words));
    }
    @Test
    public void test_findMax_MultipleWordsSameUniqueCharsDifferentLexicographicOrder() {
        List<String> input = Arrays.asList("play", "this", "game");
        String expected = "game";
        assertEquals(expected, FindMax.findMax(input));
    }
                                    
}