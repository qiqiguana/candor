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
}