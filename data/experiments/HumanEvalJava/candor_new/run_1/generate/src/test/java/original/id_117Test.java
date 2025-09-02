package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SelectWords.
*/
class SelectWordsTest {
    @Test
    void testSelectWords_WithEmptyString_ReturnsEmptyList() {
        // Arrange
        String input = "";
        int n = 4;

        // Act
        List<Object> result = SelectWords.selectWords(input, n);

        // Assert
        assertTrue(result.isEmpty());
    }
    
    @Test
        public void testNothing(){
            SelectWords s = new SelectWords();
            }
    @Test
    public void testStaticMethodOnly() {
        assertNotEquals(0, SelectWords.selectWords("Mary had a little lamb", 4).size());
    }
    @Test
    public void testConsonantCountInMultiConsonants1() {
        java.util.List<java.lang.Object> expected = new java.util.ArrayList<>();
        java.util.List<java.lang.Object> actual = SelectWords.selectWords("hello world", 2);
        assertIterableEquals(expected, actual);
    }
    @Test
    public void testConsonantCountInSingleConsonants14Fixed() {
        java.util.List<java.lang.Object> expected = new java.util.ArrayList<>(java.util.Arrays.asList("Uncle"));
        java.util.List<java.lang.Object> actual = SelectWords.selectWords("Uncle sam", 3);
        assertIterableEquals(expected, actual);
    }
    @Test
    public void testSelectWords_with_word_having_no_consonant1() {
        String s = "aeiou";
        int n = 1;
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SelectWords.selectWords(s, n));
    }
                                    
}