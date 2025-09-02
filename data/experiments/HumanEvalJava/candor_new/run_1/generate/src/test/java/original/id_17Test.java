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
    void testParseMusic_EmptyString_ReturnsEmptyList() {
        String musicString = "";
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, ParseMusic.parseMusic(musicString));
    }
    
    @Test
        public void testNothing(){
            ParseMusic s = new ParseMusic();
            }
    @Test
    public void testEmptyMusicString() {
        List result = ParseMusic.parseMusic(" ");
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
    public void testSingleQuarterNote() {
    	List<Object> result = ParseMusic.parseMusic(".|");
    	assertEquals(1, result.size());
    	assertEquals(1, result.get(0));
    }
    @Test
    public void testMultipleWholeNotes() {
    	List<Object> result = ParseMusic.parseMusic("ooo");
    	assertEquals(3, result.size());
    	for (Object note : result) {
    		assertEquals(4, note);
    	}
    }
                                    
}