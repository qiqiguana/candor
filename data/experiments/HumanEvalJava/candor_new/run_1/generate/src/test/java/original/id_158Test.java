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
    void testFindMax_WhenMultipleStringsHaveSameMaxUniqueChars_ReturnFirstInLexicographicalOrder() {
        List<String> words = new ArrayList<>();
        words.add("play");
        words.add("this");
        words.add("game");
        words.add("of");
        words.add("footbott");

        String result = FindMax.findMax(words);
        assertEquals("footbott", result);
    }
    
    @Test
        public void testNothing(){
            FindMax s = new FindMax();
            }
    @Test
    void testFindMaxWithNullInput() {
        assertNull(FindMax.findMax(null));
    }
    @Test
    void testFindMaxWithEmptyList() {
        assertNull(FindMax.findMax(List.of()));
    }
                                    
}