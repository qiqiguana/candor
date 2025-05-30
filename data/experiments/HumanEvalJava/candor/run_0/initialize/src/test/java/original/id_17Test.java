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
    void testParseMusic_WholeNotes() {
        String input = "o o o o";
        List<Object> expectedOutput = new ArrayList<>();
        expectedOutput.add(4);
        expectedOutput.add(4);
        expectedOutput.add(4);
        expectedOutput.add(4);
        assertEquals(expectedOutput, ParseMusic.parseMusic(input));
    }
    
    @Test
        public void testNothing(){
            ParseMusic s = new ParseMusic();
            }
    @Test
    public void testParseMusicClassInitialization() {
        // Given
        String musicString = "";
        
        // When
        List<Object> result = ParseMusic.parseMusic(musicString);
        
        // Then
        assertNotNull(result);
    }
    @Test
    public void testQuarterNoteWithoutPipe() {
        // Given
        String musicString = ".";
        List<Object> expectedResult = new ArrayList<>();
        expectedResult.add(1);
        // When
        List<Object> result = ParseMusic.parseMusic(musicString);
        // Then
        assertEquals(expectedResult, result);
    }
    @Test
    public void test_parseMusic_with_half_note_at_end_of_string() {
        String[] input = {"o|"};
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        assertEquals(expected, ParseMusic.parseMusic(input[0]));
    }
                                    
}