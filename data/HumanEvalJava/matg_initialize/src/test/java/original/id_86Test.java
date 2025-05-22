package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AntiShuffle.
*/
class AntiShuffleTest {
    @Test
    void testAntiShuffle_SingleWord_ReturnsOrderedString() {
        String result = AntiShuffle.antiShuffle("hello");
        assertEquals("ehllo", result);
    }
}