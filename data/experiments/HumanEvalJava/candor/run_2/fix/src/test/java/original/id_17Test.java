package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ParseMusic.
*/
class ParseMusicTest {
    @Test
    void testParseMusic_WhenInputContainsWholeNotes_ReturnsCorrectBeats() {
        String musicString = "o o o o";
        List<Object> expected = new ArrayList<>();
        expected.add(4);
        expected.add(4);
        expected.add(4);
        expected.add(4);
        assertEquals(expected, ParseMusic.parseMusic(musicString));
    }
    
    @Test
        void testNothing(){
            ParseMusic s = new ParseMusic();
            }
    @Test
    public void testEmptyString() {
        List<Object> result = ParseMusic.parseMusic("");
        assertTrue(result.isEmpty());
    }
    @Test
    public void testSingleWholeNote() {
        List<Object> result = ParseMusic.parseMusic("o");
        assertEquals(1, result.size());
        assertEquals(4, result.get(0));
    }
    @Test
    public void testSingleHalfNote() {
        List<Object> result = ParseMusic.parseMusic("o|");
        assertEquals(1, result.size());
        assertEquals(2, result.get(0));
    }
    @Test
    public void testSingleDot() {
        List<Object> result = ParseMusic.parseMusic(".");
        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }
    @Test
    public void testMultipleWholeNotes() {
        List<Object> result = ParseMusic.parseMusic("ooo");
        assertEquals(3, result.size());
        for (Object beat : result) {
            assertEquals(4, beat);
        }
    }
    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> ParseMusic.parseMusic(null));
    }
    @Test
    public void testMultipleHalfNotes() {
        List<Object> result = ParseMusic.parseMusic("ooo|");
        assertEquals(3, result.size());
        for (Object beat : result) {
            if ((int) beat == 4) {
                continue;
            } else if ((int) beat == 2) {
                continue;
            }
        }
    }
                                    
}