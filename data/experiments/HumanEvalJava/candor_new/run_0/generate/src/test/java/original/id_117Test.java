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
    void testSelectWordsWithConsonantCountOfFour() {
        String input = "Mary had a little lamb";
        int consonants = 4;
        List<Object> expected = new ArrayList<>(List.of("little"));
        assertEquals(expected, SelectWords.selectWords(input, consonants));
    }
    
    @Test
        public void testNothing(){
            SelectWords s = new SelectWords();
            }
    @Test
    public void Test_selectWords_with_consonant_count_of_0() {
        List<Object> expected = new ArrayList<>();
        List<Object> actual = SelectWords.selectWords("hello world", 0);
        assertEquals(expected, actual);
    }
    @Test
    public void testSelectWordsWithWordsContainingVowelsOnly() {
        String s = "aeiou";
        int n = 4;
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SelectWords.selectWords(s, n));
    }
                                    
}