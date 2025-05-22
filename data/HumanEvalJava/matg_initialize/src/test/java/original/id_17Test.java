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
    void testParseMusic_WholeNote() {
        String musicString = "o";
        List<Object> expected = new ArrayList<>();
        expected.add(4);
        assertEquals(expected, ParseMusic.parseMusic(musicString));
    }

}
