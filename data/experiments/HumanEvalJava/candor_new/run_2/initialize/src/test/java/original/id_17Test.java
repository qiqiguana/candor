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
        List<Object> result = ParseMusic.parseMusic("");
        assertEquals(new ArrayList<>(), result);
    }
}